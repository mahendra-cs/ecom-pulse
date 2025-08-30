package com.ecompulse.payment.controller;

import com.ecompulse.payment.ChaosState;
import com.ecompulse.payment.dto.ChaosDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chaos")
public class ChaosController {

    private final ChaosState chaosState;

    public ChaosController(ChaosState chaosState) {
        this.chaosState = chaosState;
    }

    @PostMapping("/enable")
    public void enableChaos(@RequestBody ChaosDto config) {
        chaosState.setEnabled(true);
        chaosState.setLatency(config.getLatency());
        chaosState.setErrorRate(config.getErrorRate());
    }

    @PostMapping("/disable")
    public void disableChaos() {
        chaosState.setEnabled(false);
    }
}
