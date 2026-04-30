package remitly.stockmarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import remitly.stockmarket.type.TradeType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType type;

    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    public AuditLog(TradeType type, Long walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
    }
}
