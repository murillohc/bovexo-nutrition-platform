package com.bovexo.feed_cost_service.controller;

import com.bovexo.feed_cost_service.dto.FeedCostResponse;
import com.bovexo.feed_cost_service.enums.FeedType;
import com.bovexo.feed_cost_service.service.FeedCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedCostController {

    @Autowired
    FeedCostService feedCostService;

    @GetMapping("/cost/{feedType}")
    public ResponseEntity<FeedCostResponse> findByfeedType(@PathVariable FeedType feedType) {
        return ResponseEntity.ok(feedCostService.findByFeedType(feedType));
    }
}
