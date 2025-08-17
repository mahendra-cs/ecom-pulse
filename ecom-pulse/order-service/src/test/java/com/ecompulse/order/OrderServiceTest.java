package com.ecompulse.order;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(new SimpleMeterRegistry(), restTemplate);
    }

    @Test
    public void testProcessOrder() {
        when(restTemplate.postForEntity(anyString(), any(), any(Class.class))).thenReturn(null);

        Map<String, Object> order = new HashMap<>();
        order.put("item", "test");
        order.put("quantity", 1);

        boolean result = orderService.processOrder(order);

        assertTrue(result);
    }
}
