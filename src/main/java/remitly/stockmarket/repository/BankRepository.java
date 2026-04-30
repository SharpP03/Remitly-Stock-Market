package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.stockmarket.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
