package remitly.stockmarket.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import remitly.stockmarket.type.TradeType;

@Getter
@Setter
public class StockDTO {

    @NotBlank
    @Min(0)
    private int quantity = 1;

    @NotBlank
    private TradeType type;
}
