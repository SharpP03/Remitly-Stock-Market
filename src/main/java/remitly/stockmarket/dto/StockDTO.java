package remitly.stockmarket.dto;

import lombok.Getter;
import lombok.Setter;
import remitly.stockmarket.type.TradeType;

@Getter
@Setter
public class StockDTO {
    private int quantity;

    private TradeType type;
}
