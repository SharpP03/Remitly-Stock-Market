package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import remitly.stockmarket.model.Wallet;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {
    @Query("select w from Wallet w left join fetch w.positions p left join fetch p.stock where w.id = :id order by p.stock.name")
    Optional<Wallet> findByIdWithPositions(@Param("id") String id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from Wallet w where w.id = :id")
    Optional<Wallet> findByIdForUpdate(@Param("id") String id);
}
