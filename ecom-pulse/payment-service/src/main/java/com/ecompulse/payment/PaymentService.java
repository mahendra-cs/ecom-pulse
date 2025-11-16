package com.ecompulse.payment;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
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
                log.info("Payment service called for order: {}", order);
                System.out.println("Payment service called!");
                return true;
            } catch (Exception e) {
                log.error("Payment processing failed for order: {}. Error: {}", order, e.getMessage(), e);
                return false;
            }
        });
    }
}
