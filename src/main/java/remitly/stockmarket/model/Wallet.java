package remitly.stockmarket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wallet {
    @Id
    private Long id;

    @OneToMany(mappedBy = "wallet")
    private List<WalletPosition> positions = new ArrayList<>();

    public Wallet(Long id) {
        this.id = id;
    }
}
