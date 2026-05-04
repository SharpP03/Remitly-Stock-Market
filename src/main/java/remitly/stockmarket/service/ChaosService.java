package remitly.stockmarket.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChaosService {
    private final boolean enabled;
    private final long delayMs;

    public ChaosService(
            @Value("${app.chaos.enabled:true}") boolean enabled,
            @Value("${app.chaos.delay-ms:250}") long delayMs
    ) {
        this.enabled = enabled;
        this.delayMs = delayMs;
    }

    public void terminateCurrentInstance() {
        if (!enabled) {
            return;
        }

        Thread terminator = new Thread(() -> {
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }, "chaos-terminator");
        terminator.start();
    }
}
