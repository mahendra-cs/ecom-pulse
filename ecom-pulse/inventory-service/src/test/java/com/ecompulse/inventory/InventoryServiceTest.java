package com.ecompulse.inventory;

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
public class InventoryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryService = new InventoryService(new SimpleMeterRegistry(), restTemplate);
    }

    @Test
    public void testReserve() {
        Map<String, Object> order = new HashMap<>();
        order.put("item", "test");
        order.put("quantity", 1);

        boolean result = inventoryService.reserve(order);

        assertTrue(result);
    }
}