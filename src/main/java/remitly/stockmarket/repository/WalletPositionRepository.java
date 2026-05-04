package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;
import remitly.stockmarket.model.WalletPosition;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface WalletPositionRepository extends JpaRepository<WalletPosition, Long> {
    Optional<WalletPosition> findByWalletAndStock(Wallet wallet, Stock stock);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select wp from WalletPosition wp where wp.wallet = :wallet and wp.stock = :stock")
    Optional<WalletPosition> findByWalletAndStockForUpdate(@Param("wallet") Wallet wallet, @Param("stock") Stock stock);
}
