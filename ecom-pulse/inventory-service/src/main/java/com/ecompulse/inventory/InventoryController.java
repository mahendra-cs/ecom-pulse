package com.ecompulse.inventory;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody Map<String, Object> order) {
        boolean success = inventoryService.reserve(order);
        return success ?
                ResponseEntity.ok("Inventory reserved") :
                ResponseEntity.status(500).body("Inventory reservation failed");
    }

    @GetMapping("/simulate-latency/{seconds}")
    @Operation(summary = "Simulate latency in the inventory service")
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
