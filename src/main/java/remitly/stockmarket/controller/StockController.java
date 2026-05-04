package remitly.stockmarket.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.dto.StocksDTO;
import remitly.stockmarket.dto.response.AuditLogDTO;
import remitly.stockmarket.dto.response.WalletDTO;
import remitly.stockmarket.service.AuditLogService;
import remitly.stockmarket.service.StockService;
import remitly.stockmarket.service.WalletService;

@RestController
public class StockController {
    private final WalletService walletService;
    private final StockService stockService;
    private final AuditLogService auditLogService;

    public StockController(WalletService walletService, StockService stockService, AuditLogService auditLogService) {
        this.walletService = walletService;
        this.stockService = stockService;
        this.auditLogService = auditLogService;
    }

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> trade(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @Valid @RequestBody StockDTO stockDTO
    ) {
        walletService.trade(walletId, stockName, stockDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable String walletId) {
        return ResponseEntity.ok(walletService.getWallet(walletId));
    }

    @GetMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Integer> getWalletStockQuantity(
            @PathVariable String walletId,
            @PathVariable String stockName
    ) {
        return ResponseEntity.ok(walletService.getStockQuantity(walletId, stockName));
    }

    @GetMapping("/stocks")
    public ResponseEntity<StocksDTO> getStocks() {
        return ResponseEntity.ok(stockService.getStocks());
    }

    @PostMapping("/stocks")
    public ResponseEntity<Void> setStocks(@Valid @RequestBody StocksDTO stocksDTO) {
        stockService.setStocks(stocksDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/log")
    public ResponseEntity<AuditLogDTO> getLog() {
        return ResponseEntity.ok(auditLogService.getLog());
    }

}
