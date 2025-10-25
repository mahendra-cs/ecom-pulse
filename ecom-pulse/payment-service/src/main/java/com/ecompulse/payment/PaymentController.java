package com.ecompulse.payment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody Map<String, Object> order) {
        log.info("Received payment request: {}", order);
        boolean success = paymentService.pay(order);
        if (success) {
            log.info("Payment successful for order: {}", order);
            return ResponseEntity.ok("Payment Done");
        } else {
            log.error("Payment failed for order: {}", order);
            return ResponseEntity.status(500).body("Payment failed");
        }
    }

    @GetMapping("/simulate-latency/{seconds}")
    @Operation(summary = "Simulate latency in the payment service")
    public ResponseEntity<String> simulateLatency(@PathVariable int seconds) {
        log.info("Simulating latency of {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000);
            return ResponseEntity.ok("Latency of " + seconds + " seconds simulated");
        } catch (InterruptedException e) {
            log.error("Error during latency simulation", e);
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Error during latency simulation");
        }
    }
}
