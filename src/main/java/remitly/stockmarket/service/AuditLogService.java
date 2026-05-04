package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.response.AuditLogDTO;
import remitly.stockmarket.dto.response.AuditLogEntryDTO;
import remitly.stockmarket.model.AuditLog;
import remitly.stockmarket.repository.AuditLogRepository;
import remitly.stockmarket.type.TradeType;

import java.util.Locale;

@AllArgsConstructor
@Service
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void logSuccessfulTrade(TradeType type, String walletId, String stockName) {
        auditLogRepository.save(new AuditLog(type, walletId, stockName));
    }

    @Transactional
    public AuditLogDTO getLog() {
        AuditLogDTO response = new AuditLogDTO();
        response.setLog(auditLogRepository.findAllByOrderByIdAsc().stream()
                .map(log -> {
                    AuditLogEntryDTO dto = new AuditLogEntryDTO();
                    dto.setType(log.getType().name().toLowerCase(Locale.ROOT));
                    dto.setWalletId(log.getWalletId());
                    dto.setStockName(log.getStockName());
                    return dto;
                })
                .toList());

        return response;
    }
}
