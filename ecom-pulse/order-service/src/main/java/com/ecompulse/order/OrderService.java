package com.ecompulse.order;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final Timer orderTimer;

    public OrderService(MeterRegistry registry, RestTemplate restTemplate) {
        this.orderTimer = Timer.builder("order.processing.duration")
                .description("Time taken to process orders")
                .register(registry);
        this.restTemplate = restTemplate;
    }

    public boolean processOrder(Map<String, Object> order) {
        return orderTimer.record(() -> {
            try {
                restTemplate.postForEntity("http://inventory-service:8081/inventory/reserve", order, String.class);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
