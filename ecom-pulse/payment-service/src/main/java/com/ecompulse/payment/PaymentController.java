package com.ecompulse.payment;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody Map<String, Object> order) {
        boolean success = paymentService.pay(order);
        return success ?
                ResponseEntity.ok("Payment Done") :
                ResponseEntity.status(500).body("Payment failed");
    }

    @GetMapping("/simulate-latency/{seconds}")
    @Operation(summary = "Simulate latency in the payment service")
    public ResponseEntity<String> simulateLatency(@PathVariable int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            return ResponseEntity.ok("Latency of " + seconds + " seconds simulated");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Error during latency simulation");
        }
    }
}
