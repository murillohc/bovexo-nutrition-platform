package com.bovexo.feed_cost_service.entity;

import com.bovexo.feed_cost_service.enums.FeedType;
import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "feed_cost")
public class FeedCost {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(name = "feed_type", nullable = false, unique = true)
    private FeedType feedType;
    @Column(name = "cost_per_kg", nullable = false)
    private BigDecimal costPerKg;
    @LastModifiedDate
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FeedType getFeedType() {
        return feedType;
    }

    public void setFeedType(FeedType feedType) {
        this.feedType = feedType;
    }

    public BigDecimal getCostPerKg() {
        return costPerKg;
    }

    public void setCostPerKg(BigDecimal costPerKg) {
        this.costPerKg = costPerKg;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
