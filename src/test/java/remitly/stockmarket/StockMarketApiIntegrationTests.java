package remitly.stockmarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import remitly.stockmarket.repository.AuditLogRepository;
import remitly.stockmarket.repository.BankRepository;
import remitly.stockmarket.repository.BankStockRepository;
import remitly.stockmarket.repository.StockRepository;
import remitly.stockmarket.repository.WalletPositionRepository;
import remitly.stockmarket.repository.WalletRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class StockMarketApiIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private WalletPositionRepository walletPositionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BankStockRepository bankStockRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void cleanDatabase() {
        auditLogRepository.deleteAll();
        walletPositionRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockRepository.deleteAll();
        bankRepository.deleteAll();
        stockRepository.deleteAll();
    }

    @Test
    void supportsRequiredTradeFlowAndAuditLog() throws Exception {
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"stocks":[
                                  {"name":"stock1","quantity":2},
                                  {"name":"stock2","quantity":0}
                                ]}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks", hasSize(2)))
                .andExpect(jsonPath("$.stocks[0].name").value("stock1"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(2));

        mockMvc.perform(post("/wallets/12qdsdadsa/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"buy\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/12qdsdadsa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("12qdsdadsa"))
                .andExpect(jsonPath("$.stocks", hasSize(1)))
                .andExpect(jsonPath("$.stocks[0].name").value("stock1"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));

        mockMvc.perform(get("/wallets/12qdsdadsa/stocks/stock1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));

        mockMvc.perform(post("/wallets/12qdsdadsa/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"sell\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/12qdsdadsa/stocks/stock1"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks[0].quantity").value(2));

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log", hasSize(2)))
                .andExpect(jsonPath("$.log[0].type").value("buy"))
                .andExpect(jsonPath("$.log[0].wallet_id").value("12qdsdadsa"))
                .andExpect(jsonPath("$.log[0].stock_name").value("stock1"))
                .andExpect(jsonPath("$.log[1].type").value("sell"));
    }

    @Test
    void returnsRequiredErrorsAndDoesNotAuditFailedTrades() throws Exception {
        mockMvc.perform(post("/wallets/wallet-a/stocks/missing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"buy\"}"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stocks\":[{\"name\":\"empty\",\"quantity\":0}]}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/wallets/wallet-a/stocks/empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"buy\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post("/wallets/wallet-a/stocks/empty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"sell\"}"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log", hasSize(0)));
    }

    @Test
    void exposesChaosEndpoint() throws Exception {
        mockMvc.perform(post("/chaos"))
                .andExpect(status().isOk());
    }
}
