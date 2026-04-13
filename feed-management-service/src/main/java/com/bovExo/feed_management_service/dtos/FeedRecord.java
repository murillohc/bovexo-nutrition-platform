package com.bovExo.feed_management_service.dtos;

import com.bovExo.feed_management_service.enums.FeedType;
import jakarta.validation.constraints.NotNull;

public record FeedRecord(@NotNull Long animalId,
                         @NotNull FeedType feedType,
                         @NotNull Double quantity) {
}
