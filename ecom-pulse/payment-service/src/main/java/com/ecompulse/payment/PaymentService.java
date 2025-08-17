package com.ecompulse.payment;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final Timer paymentTimer;

    public PaymentService(MeterRegistry registry, RestTemplate restTemplate) {
        this.paymentTimer = Timer.builder("payment.processing.duration")
                .description("Time taken to process payments")
                .register(registry);
        this.restTemplate = restTemplate;
    }

    public boolean pay(Map<String, Object> order) {
        return paymentTimer.record(() -> {
            try {
                // In a real app, you'd call a payment gateway here
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
