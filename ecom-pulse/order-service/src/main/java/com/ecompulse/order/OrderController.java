package com.ecompulse.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> order) {
        boolean success = orderService.processOrder(order);
        return success ?
                ResponseEntity.ok("Order placed successfully") :
                ResponseEntity.status(500).body("Failed to place order");
    }
}
