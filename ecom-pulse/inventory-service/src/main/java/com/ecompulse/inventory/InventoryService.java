package com.ecompulse.inventory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
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
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
