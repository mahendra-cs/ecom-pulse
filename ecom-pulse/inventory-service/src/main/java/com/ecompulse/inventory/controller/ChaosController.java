package com.ecompulse.inventory.controller;

import com.ecompulse.inventory.ChaosState;
import com.ecompulse.inventory.dto.ChaosDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chaos")
@Slf4j
public class ChaosController {

    private final ChaosState chaosState;

    public ChaosController(ChaosState chaosState) {
        this.chaosState = chaosState;
    }

    @PostMapping("/enable")
    public void enableChaos(@RequestBody ChaosDto config) {
        log.info("Enabling chaos with config: {}", config);
        chaosState.setEnabled(true);
        chaosState.setLatency(config.getLatency());
        chaosState.setErrorRate(config.getErrorRate());
    }

    @PostMapping("/disable")
    public void disableChaos() {
        log.info("Disabling chaos");
        chaosState.setEnabled(false);
    }
}
