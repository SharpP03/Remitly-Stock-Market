package remitly.stockmarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import remitly.stockmarket.type.TradeType;

public class WalletPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(unique = true)
    private Wallet wallet;

    @Column(unique = true)
    private Stock stock;

    private int quantity;
}
