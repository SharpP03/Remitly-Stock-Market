package remitly.stockmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.model.Stock;
import remitly.stockmarket.repository.StockRepository;
import remitly.stockmarket.service.StockService;
import remitly.stockmarket.service.WalletService;
import remitly.stockmarket.type.TradeType;

@RestController
public class StockController {
    private final StockService stockService;
    private final WalletService walletService;

    public StockController(StockService stockService, WalletService walletService) {
        this.stockService = stockService;
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
