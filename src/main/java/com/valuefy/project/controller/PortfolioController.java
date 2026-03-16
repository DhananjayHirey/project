package com.valuefy.project.controller;

import com.valuefy.project.dto.RebalanceItemDTO;
import com.valuefy.project.model.ClientHolding;
import com.valuefy.project.model.ModelFund;
import com.valuefy.project.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService service;

    // Amit's current investments
    @GetMapping("/{clientId}/holdings")
    public List<ClientHolding> getHoldings(
            @PathVariable String clientId) {

        return service.getHoldings(clientId);
    }

    // Advisor's recommended plan
    @GetMapping("/model-funds")
    public List<ModelFund> getModelFunds() {

        return service.getModelFunds();
    }

    @RestController
    @RequestMapping("/api/portfolio")
    @RequiredArgsConstructor
    public class PortfolioController {

        private final PortfolioService service;

        @GetMapping("/{clientId}/rebalance")
        public List<RebalanceItemDTO> rebalance(
                @PathVariable String clientId) {

            return service.getRebalance(clientId);
        }
    }
}