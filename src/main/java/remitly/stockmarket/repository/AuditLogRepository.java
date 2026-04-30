package remitly.stockmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import remitly.stockmarket.model.AuditLog;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findAllByOrderByIdAsc();
}
