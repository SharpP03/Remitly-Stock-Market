package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.stockmarket.model.Stock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByName(String name);
}
