package remitly.stockmarket.type;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum TradeType {
    BUY,
    SELL;

    @JsonCreator
    public static TradeType fromJson(String value) {
        if (value == null) {
            return null;
        }

        return TradeType.valueOf(value.toUpperCase(Locale.ROOT));
    }
}
