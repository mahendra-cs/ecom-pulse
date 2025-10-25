package com.ecompulse.inventory;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory")
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody Map<String, Object> order) {
        log.info("Received inventory reservation request: {}", order);
        boolean success = inventoryService.reserve(order);
        if (success) {
            log.info("Inventory reserved successfully for order: {}", order);
            return ResponseEntity.ok("Inventory reserved");
        } else {
            log.error("Inventory reservation failed for order: {}", order);
            return ResponseEntity.status(500).body("Inventory reservation failed");
        }
    }

    @GetMapping("/simulate-latency/{seconds}")
    @Operation(summary = "Simulate latency in the inventory service")
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
