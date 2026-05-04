package remitly.stockmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import remitly.stockmarket.service.ChaosService;

@RestController
public class ChaosController {
    private final ChaosService chaosService;

    public ChaosController(ChaosService chaosService) {
        this.chaosService = chaosService;
    }

    @PostMapping("/chaos")
    public ResponseEntity<Void> chaos() {
        chaosService.terminateCurrentInstance();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<Void> health() {
        return ResponseEntity.ok().build();
    }
}
