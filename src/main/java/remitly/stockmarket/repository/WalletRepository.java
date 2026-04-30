package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import remitly.stockmarket.model.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("select w from Wallet w left join fetch w.positions where w.id = :id")
    Optional<Wallet> findByIdWithPositions(@Param("id") Long id);
}
