package remitly.stockmarket.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import remitly.stockmarket.dto.StockDTO;
import remitly.stockmarket.repository.StockRepository;
import remitly.stockmarket.repository.WalletRepository;
import remitly.stockmarket.type.TradeType;

@Service
public class WalletService {

    @Transactional
    public void trade(Long walletId,String stockName,StockDTO stockDTO) {
        if (stockDTO.getType() == TradeType.BUY) {


        } else if (stockDTO.getType() == TradeType.SELL) {

        }
    }
}
