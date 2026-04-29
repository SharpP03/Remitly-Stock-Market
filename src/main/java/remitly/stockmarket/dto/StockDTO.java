package remitly.stockmarket.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import remitly.stockmarket.type.TradeType;

@Getter
@Setter
public class StockDTO {

    @Positive(message = "Quantity must be greater than 0")
    private int quantity = 1;

    @NotNull(message = "Trade type is required")
    private TradeType type;
}
