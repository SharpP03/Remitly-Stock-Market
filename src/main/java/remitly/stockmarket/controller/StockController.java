package remitly.stockmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.service.WalletService;

@RestController
public class StockController {
    private final WalletService walletService;

    public StockController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> trade(
            @PathVariable Long walletId,
            @PathVariable String stockName,
            @RequestBody StockDTO stockDTO
    ){
        walletService.trade(walletId,stockName,stockDTO);

        return ResponseEntity.ok().build();
    }

}
