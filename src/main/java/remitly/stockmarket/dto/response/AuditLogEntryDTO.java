package remitly.stockmarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditLogEntryDTO {
    private String type;

    @JsonProperty("wallet_id")
    private String walletId;

    @JsonProperty("stock_name")
    private String stockName;
}
