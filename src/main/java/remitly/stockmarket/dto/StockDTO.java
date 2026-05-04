package remitly.stockmarket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import remitly.stockmarket.type.TradeType;

@Getter
@Setter
public class StockDTO {

    @NotNull(message = "Trade type is required")
    private TradeType type;
}
