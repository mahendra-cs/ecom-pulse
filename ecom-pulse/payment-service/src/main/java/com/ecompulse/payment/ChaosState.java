package com.ecompulse.payment;

import org.springframework.stereotype.Component;

@Component
public class ChaosState {

    private boolean enabled = false;
    private int latency = 0;
    private double errorRate = 0.0;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }
}
