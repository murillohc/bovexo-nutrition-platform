package com.bovexo.nutrition_analysis_service.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FeedCostResponse(@NotNull String feedType,
                               @NotNull BigDecimal costPerKg) {
    @Override
    public String feedType() {
        return feedType;
    }

    @Override
    public BigDecimal costPerKg() {
        return costPerKg;
    }
}
