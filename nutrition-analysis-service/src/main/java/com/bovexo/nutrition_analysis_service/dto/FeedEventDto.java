package com.bovexo.nutrition_analysis_service.dto;
import jakarta.validation.constraints.NotNull;


public record FeedEventDto( @NotNull Long animalId,
                            @NotNull String feedType,
                            @NotNull Double quantity) {
    @Override
    public String feedType() {
        return feedType;
    }

    @Override
    public Double quantity() {
        return quantity;
    }

    @Override
    public Long animalId() {
        return animalId;
    }
}
