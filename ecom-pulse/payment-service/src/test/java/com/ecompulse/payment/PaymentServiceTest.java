package com.ecompulse.payment;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService(new SimpleMeterRegistry(), restTemplate);
    }

    @Test
    public void testPay() {
        Map<String, Object> order = new HashMap<>();
        order.put("item", "test");
        order.put("quantity", 1);

        boolean result = paymentService.pay(order);

        assertTrue(result);
    }
}