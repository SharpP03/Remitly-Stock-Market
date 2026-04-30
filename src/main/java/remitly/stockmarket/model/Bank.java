package remitly.stockmarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bank {
    public static final Long DEFAULT_ID = 1L;

    @Id
    private Long id;

    public Bank(Long id) {
        this.id = id;
    }
}
