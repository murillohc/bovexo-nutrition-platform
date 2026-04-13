package com.bovExo.feed_management_service.repository;

import com.bovExo.feed_management_service.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {

    List<Feed> findByAnimalId(Long animalId);
}
