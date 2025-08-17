package com.ecompulse.payment;

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
}
