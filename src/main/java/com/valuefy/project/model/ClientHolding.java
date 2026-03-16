package com.valuefy.project.model;

import lombok.Data;

@Data
public class ClientHolding {

    private Long holdingId;
    private String clientId;
    private String fundId;
    private String fundName;
    private Double currentValue;
}
