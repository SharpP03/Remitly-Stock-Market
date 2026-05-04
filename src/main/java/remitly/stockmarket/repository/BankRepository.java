package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import remitly.stockmarket.model.Bank;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Bank b where b.id = :id")
    Optional<Bank> findByIdForUpdate(@Param("id") Long id);
}
