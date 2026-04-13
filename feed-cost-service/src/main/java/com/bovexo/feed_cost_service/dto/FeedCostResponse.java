package com.bovexo.feed_cost_service.dto;

import com.bovexo.feed_cost_service.enums.FeedType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FeedCostResponse(@NotNull FeedType feedType,
                               @NotNull BigDecimal costPerKg) {
}
