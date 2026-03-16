package com.valuefy.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RebalanceItemDTO {

    private String fundId;
    private String fundName;

    private double currentPct;
    private double targetPct;

    private double drift;

    private String action;

    private double amount;

    private boolean isModelFund;
}