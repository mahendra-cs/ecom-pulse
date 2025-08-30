package com.ecompulse.payment.interceptor;

import com.ecompulse.payment.ChaosState;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Random;

@Component
public class ChaosInterceptor implements HandlerInterceptor {

    private final ChaosState chaosState;
    private final Random random = new Random();

    public ChaosInterceptor(ChaosState chaosState) {
        this.chaosState = chaosState;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (chaosState.isEnabled() && !request.getRequestURI().contains("/chaos")) {
            // Simulate latency
            if (chaosState.getLatency() > 0) {
                Thread.sleep(chaosState.getLatency());
            }

            // Simulate errors
            if (chaosState.getErrorRate() > 0 && random.nextDouble() < chaosState.getErrorRate()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Chaos monkey is at work!");
                return false;
            }
        }
        return true;
    }
}
