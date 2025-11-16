package com.ecompulse.inventory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class InventoryService {

    private final RestTemplate restTemplate;
    private final Timer inventoryTimer;

    public InventoryService(MeterRegistry registry, RestTemplate restTemplate) {
        this.inventoryTimer = Timer.builder("inventory.reserving.duration")
                .description("Time taken to reserve inventory")
                .register(registry);
        this.restTemplate = restTemplate;
    }

    public boolean reserve(Map<String, Object> order) {
        return inventoryTimer.record(() -> {
            try {
                // In a real app, you'd interact with a database here
                restTemplate.postForEntity("http://payment-service:8082/payment/pay", order, String.class);
                return true;
            } catch (Exception e) {
                log.error("Failed to process payment for order {}: {}", order, e.getMessage(), e);
                return false;
            }
        });
    }
}
