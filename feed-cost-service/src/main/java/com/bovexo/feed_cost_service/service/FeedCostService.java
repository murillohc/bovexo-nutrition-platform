package com.bovexo.feed_cost_service.service;

import com.bovexo.feed_cost_service.dto.FeedCostResponse;
import com.bovexo.feed_cost_service.entity.FeedCost;
import com.bovexo.feed_cost_service.enums.FeedType;
import com.bovexo.feed_cost_service.repository.FeedCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FeedCostService {

    @Autowired
    FeedCostRepository feedCostRepository;

    public FeedCostResponse findByFeedType(FeedType feedType) {
        FeedCost feedCost = feedCostRepository.findByFeedType(feedType)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Feed type not found: " + feedType));

        return new FeedCostResponse(feedCost.getFeedType(), feedCost.getCostPerKg());
    }
}
