package com.valuefy.project.model;

import lombok.Data;

@Data
public class ModelFund {

    private String fundId;
    private String fundName;
    private String assetClass;
    private Double allocationPct;
}