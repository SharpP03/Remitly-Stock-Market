package remitly.stockmarket.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AuditLogDTO {
    private List<AuditLogEntryDTO> log = new ArrayList<>();
}
