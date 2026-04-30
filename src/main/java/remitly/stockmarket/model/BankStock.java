package remitly.stockmarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"bank_id", "stock_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class BankStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer quantity;

    public BankStock(Bank bank, Stock stock, Integer quantity) {
        this.bank = bank;
        this.stock = stock;
        this.quantity = quantity;
    }
}
