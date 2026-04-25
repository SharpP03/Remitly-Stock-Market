package remitly.stockmarket.repository;

import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;
import remitly.stockmarket.model.WalletPosition;

import java.util.Optional;

public interface WalletPositionRepository {
    Optional<WalletPosition> findByWalletAndStock(Wallet wallet, Stock stock);
}
