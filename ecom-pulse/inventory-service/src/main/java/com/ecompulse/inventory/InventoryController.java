package com.ecompulse.inventory;

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
}
