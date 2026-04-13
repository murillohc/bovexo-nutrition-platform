package com.bovexo.feed_cost_service.repository;

import com.bovexo.feed_cost_service.entity.FeedCost;
import com.bovexo.feed_cost_service.enums.FeedType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FeedCostRepository extends JpaRepository<FeedCost, UUID> {

    Optional<FeedCost> findByFeedType(FeedType feedType);
}
