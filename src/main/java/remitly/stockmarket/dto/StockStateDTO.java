package remitly.stockmarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockStateDTO {
    @NotBlank(message = "Stock name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
