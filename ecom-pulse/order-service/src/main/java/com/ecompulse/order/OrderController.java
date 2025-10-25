package com.ecompulse.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> order) {
        log.info("Received order creation request: {}", order);
        boolean success = orderService.processOrder(order);
        if (success) {
            log.info("Order placed successfully: {}", order);
            return ResponseEntity.ok("Order placed successfully");
        } else {
            log.error("Failed to place order: {}", order);
            return ResponseEntity.status(500).body("Failed to place order");
        }
    }
}
