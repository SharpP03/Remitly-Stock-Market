package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.dto.response.WalletDTO;
import remitly.stockmarket.dto.response.WalletStockDTO;
import remitly.stockmarket.exception.NoStockException;
import remitly.stockmarket.exception.NoWalletException;
import remitly.stockmarket.exception.NotEnoughStockException;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;
import remitly.stockmarket.model.WalletPosition;
import remitly.stockmarket.repository.StockRepository;
import remitly.stockmarket.repository.WalletPositionRepository;
import remitly.stockmarket.repository.WalletRepository;
import remitly.stockmarket.type.TradeType;

@AllArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final StockRepository stockRepository;
    private final WalletPositionRepository walletPositionRepository;

    @Transactional
    public void trade(Long walletId, String stockName, StockDTO stockDTO) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new NoWalletException(walletId));
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new NoStockException(stockName));
        WalletPosition position = walletPositionRepository.findByWalletAndStock(wallet, stock).orElse(null);

        switch (stockDTO.getType()) {
            case BUY -> buy(wallet, stock, position, stockDTO.getQuantity());
            case SELL -> sell(position, stockDTO.getQuantity());
        }
    }

    private void buy(Wallet wallet, Stock stock, WalletPosition position, int quantity) {
        if (position == null) {
            WalletPosition newPosition = new WalletPosition();
            newPosition.setWallet(wallet);
            newPosition.setStock(stock);
            newPosition.setQuantity(quantity);
            walletPositionRepository.save(newPosition);
            return;
        }

        position.setQuantity(position.getQuantity() + quantity);
        walletPositionRepository.save(position);
    }

    private void sell(WalletPosition position, int quantity) {
        if (position == null || position.getQuantity() < quantity) {
            throw new NotEnoughStockException();
        }

        int remainingQuantity = position.getQuantity() - quantity;
        if (remainingQuantity == 0) {
            walletPositionRepository.delete(position);
            return;
        }

        position.setQuantity(remainingQuantity);
        walletPositionRepository.save(position);
    }

    @Transactional
    public WalletDTO getWallet(Long walletId) {
        Wallet wallet = walletRepository.findByIdWithPositions(walletId)
                .orElseThrow(() -> new NoWalletException(walletId));

        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setId(wallet.getId());
        walletDTO.setStocks(wallet.getPositions().stream()
                .map(position -> {
                    WalletStockDTO stockDTO = new WalletStockDTO();
                    stockDTO.setName(position.getStock().getName());
                    stockDTO.setQuantity(position.getQuantity());
                    return stockDTO;
                })
                .toList());

        return walletDTO;
    }
}
