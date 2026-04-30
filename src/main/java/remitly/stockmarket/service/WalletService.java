package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.dto.response.WalletDTO;
import remitly.stockmarket.dto.response.WalletStockDTO;
import remitly.stockmarket.exception.NoWalletException;
import remitly.stockmarket.exception.NotEnoughStockException;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;
import remitly.stockmarket.model.WalletPosition;
import remitly.stockmarket.repository.WalletPositionRepository;
import remitly.stockmarket.repository.WalletRepository;

@AllArgsConstructor
@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final StockService stockService;
    private final WalletPositionRepository walletPositionRepository;
    private final AuditLogService auditLogService;

    @Transactional
    public void trade(Long walletId, String stockName, StockDTO stockDTO) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseGet(() -> walletRepository.save(new Wallet(walletId)));
        Stock stock = stockService.getExistingStock(stockName);
        WalletPosition position = walletPositionRepository.findByWalletAndStock(wallet, stock).orElse(null);

        switch (stockDTO.getType()) {
            case BUY -> buy(wallet, stock, position);
            case SELL -> sell(stock, position);
        }

        auditLogService.logSuccessfulTrade(stockDTO.getType(), wallet.getId(), stock.getName());
    }

    private void buy(Wallet wallet, Stock stock, WalletPosition position) {
        stockService.sellOneStockToWallet(stock);

        if (position == null) {
            WalletPosition newPosition = new WalletPosition();
            newPosition.setWallet(wallet);
            newPosition.setStock(stock);
            newPosition.setQuantity(1);
            walletPositionRepository.save(newPosition);
            return;
        }

        position.setQuantity(position.getQuantity() + 1);
        walletPositionRepository.save(position);
    }

    private void sell(Stock stock, WalletPosition position) {
        if (position == null || position.getQuantity() <= 0) {
            throw new NotEnoughStockException();
        }

        int remainingQuantity = position.getQuantity() - 1;
        if (remainingQuantity == 0) {
            walletPositionRepository.delete(position);
        } else {
            position.setQuantity(remainingQuantity);
            walletPositionRepository.save(position);
        }

        stockService.buyOneStockFromWallet(stock);
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
