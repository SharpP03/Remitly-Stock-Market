package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.StockStateDTO;
import remitly.stockmarket.dto.StocksDTO;
import remitly.stockmarket.exception.NoBankStockException;
import remitly.stockmarket.exception.NoStockException;
import remitly.stockmarket.model.Bank;
import remitly.stockmarket.model.BankStock;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.repository.BankRepository;
import remitly.stockmarket.repository.BankStockRepository;
import remitly.stockmarket.repository.StockRepository;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class StockService {
    private final BankRepository bankRepository;
    private final BankStockRepository bankStockRepository;
    private final StockRepository stockRepository;

    @Transactional
    public StocksDTO getStocks() {
        Bank bank = getOrCreateBank();

        StocksDTO response = new StocksDTO();
        response.setStocks(bankStockRepository.findByBankWithStockOrderByName(bank).stream()
                .map(bankStock -> {
                    StockStateDTO stockDTO = new StockStateDTO();
                    stockDTO.setName(bankStock.getStock().getName());
                    stockDTO.setQuantity(bankStock.getQuantity());
                    return stockDTO;
                })
                .toList());

        return response;
    }

    @Transactional
    public void setStocks(StocksDTO stocksDTO) {
        Bank bank = getOrCreateBank();
        Set<String> stockNames = new HashSet<>();

        bankStockRepository.deleteByBank(bank);
        bankStockRepository.flush();
        stocksDTO.getStocks().forEach(stockDTO -> {
            String stockName = stockDTO.getName().trim();
            if (!stockNames.add(stockName)) {
                throw new IllegalArgumentException("Duplicate stock name: " + stockName);
            }

            Stock stock = stockRepository.findByName(stockName)
                    .orElseGet(() -> stockRepository.save(new Stock(stockName)));
            bankStockRepository.save(new BankStock(bank, stock, stockDTO.getQuantity()));
        });
    }

    public Stock getExistingStock(String stockName) {
        return stockRepository.findByName(stockName)
                .orElseThrow(() -> new NoStockException(stockName));
    }

    public void sellOneStockToWallet(Stock stock) {
        BankStock bankStock = getBankStock(stock);
        if (bankStock.getQuantity() <= 0) {
            throw new NoBankStockException(stock.getName());
        }

        bankStock.setQuantity(bankStock.getQuantity() - 1);
        bankStockRepository.save(bankStock);
    }

    public void buyOneStockFromWallet(Stock stock) {
        Bank bank = getOrCreateBank();
        BankStock bankStock = bankStockRepository.findByBankAndStock(bank, stock)
                .orElseGet(() -> new BankStock(bank, stock, 0));

        bankStock.setQuantity(bankStock.getQuantity() + 1);
        bankStockRepository.save(bankStock);
    }

    private Bank getOrCreateBank() {
        return bankRepository.findById(Bank.DEFAULT_ID)
                .orElseGet(() -> bankRepository.save(new Bank(Bank.DEFAULT_ID)));
    }

    private BankStock getBankStock(Stock stock) {
        Bank bank = getOrCreateBank();
        return bankStockRepository.findByBankAndStock(bank, stock)
                .orElseThrow(() -> new NoBankStockException(stock.getName()));
    }
}
