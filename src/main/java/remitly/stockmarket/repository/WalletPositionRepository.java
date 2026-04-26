package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;
import remitly.stockmarket.model.WalletPosition;

import java.util.Optional;

public interface WalletPositionRepository extends JpaRepository<WalletPosition, Long> {
    Optional<WalletPosition> findByWalletAndStock(Wallet wallet, Stock stock);
}
