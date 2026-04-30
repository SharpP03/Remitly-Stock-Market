package remitly.stockmarket.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StocksDTO {
    @Valid
    @NotNull(message = "Stocks list is required")
    private List<StockStateDTO> stocks = new ArrayList<>();
}
