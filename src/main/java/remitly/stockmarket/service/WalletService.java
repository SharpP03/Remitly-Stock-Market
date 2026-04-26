package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.StockDTO;
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
        if (stockDTO == null || stockDTO.getType() == null) {
            throw new IllegalArgumentException("Trade type is required");
        }
        if (stockDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));
        Stock stock = stockRepository.findByName(stockName)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
        WalletPosition position = walletPositionRepository.findByWalletAndStock(wallet, stock).orElse(null);

        if (stockDTO.getType() == TradeType.BUY) {
            if (position == null) {
                WalletPosition newPosition = new WalletPosition();
                newPosition.setWallet(wallet);
                newPosition.setStock(stock);
                newPosition.setQuantity(stockDTO.getQuantity());
                walletPositionRepository.save(newPosition);
            } else {
                position.setQuantity(position.getQuantity() + stockDTO.getQuantity());
                walletPositionRepository.save(position);
            }
        } else if (stockDTO.getType() == TradeType.SELL) {
            if (position == null || position.getQuantity() < stockDTO.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock quantity to sell");
            }
            int remainingQuantity = position.getQuantity() - stockDTO.getQuantity();
            if (remainingQuantity == 0) {
                walletPositionRepository.delete(position);
            } else {
                position.setQuantity(remainingQuantity);
                walletPositionRepository.save(position);
            }
        }
    }
}
