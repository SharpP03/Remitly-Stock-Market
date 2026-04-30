package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import remitly.stockmarket.model.Bank;
import remitly.stockmarket.model.BankStock;
import remitly.stockmarket.model.Stock;

import java.util.List;
import java.util.Optional;

public interface BankStockRepository extends JpaRepository<BankStock, Long> {
    Optional<BankStock> findByBankAndStock(Bank bank, Stock stock);

    void deleteByBank(Bank bank);

    @Query("select bs from BankStock bs join fetch bs.stock where bs.bank = :bank order by bs.stock.name")
    List<BankStock> findByBankWithStockOrderByName(@Param("bank") Bank bank);
}
