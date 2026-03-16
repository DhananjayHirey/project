package com.valuefy.project.service;

import com.valuefy.project.dto.RebalanceItemDTO;
import com.valuefy.project.model.ClientHolding;
import com.valuefy.project.model.ModelFund;
import com.valuefy.project.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository repo;

    public List<ClientHolding> getHoldings(String clientId) {
        return repo.getClientHoldings(clientId);
    }

    public List<ModelFund> getModelFunds() {
        return repo.getModelFunds();
    }

    public List<RebalanceItemDTO> getRebalance(String clientId) {

        List<ClientHolding> holdings = repo.getClientHoldings(clientId);
        List<ModelFund> modelFunds = repo.getModelFunds();

        double totalPortfolio =
                holdings.stream()
                        .mapToDouble(ClientHolding::getCurrentValue)
                        .sum();

        Map<String, ModelFund> modelMap =
                modelFunds.stream()
                        .collect(Collectors.toMap(
                                ModelFund::getFundId,
                                f -> f));

        List<RebalanceItemDTO> result = new ArrayList<>();

        for (ModelFund mf : modelFunds) {

            double currentValue = holdings.stream()
                    .filter(h -> h.getFundId().equals(mf.getFundId()))
                    .mapToDouble(ClientHolding::getCurrentValue)
                    .findFirst()
                    .orElse(0);

            double currentPct =
                    (currentValue / totalPortfolio) * 100;

            double targetPct = mf.getAllocationPct();

            double drift = targetPct - currentPct;

            double amount =
                    drift / 100 * totalPortfolio;

            String action =
                    amount > 0 ? "BUY" : "SELL";

            result.add(new RebalanceItemDTO(
                    mf.getFundId(),
                    mf.getFundName(),
                    currentPct,
                    targetPct,
                    drift,
                    action,
                    Math.abs(amount),
                    true
            ));
        }

        return result;
    }
}