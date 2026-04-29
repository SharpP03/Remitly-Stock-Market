package remitly.stockmarket.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.dto.response.WalletDTO;
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
            @Valid  @RequestBody StockDTO stockDTO
    ){
        walletService.trade(walletId,stockName,stockDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable Long walletId) {
        return ResponseEntity.ok(walletService.getWallet(walletId));
    }

}
