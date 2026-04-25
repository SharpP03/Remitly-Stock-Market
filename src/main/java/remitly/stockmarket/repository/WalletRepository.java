package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.model.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
