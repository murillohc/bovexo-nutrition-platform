package com.bovExo.feed_management_service.controller;

import com.bovExo.feed_management_service.dtos.FeedRecord;
import com.bovExo.feed_management_service.entity.Feed;
import com.bovExo.feed_management_service.services.FeedService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedController {

    @Autowired
    FeedService feedService;

    @PostMapping("/feeds")
    public ResponseEntity<Feed> saveFeed(@RequestBody @Valid FeedRecord feedRecord){
        var feed = new Feed();
        BeanUtils.copyProperties(feedRecord, feed);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedService.create(feed));
    }

    @GetMapping("/feeds")
    public ResponseEntity<List<Feed>> findAll(){
        return ResponseEntity.ok(feedService.findAll());
    }

    @GetMapping("/feeds/{animalId}")
    public ResponseEntity<List<Feed>> findByAnimalId(@PathVariable Long animalId){
        return ResponseEntity.ok(feedService.findByAnimalId(animalId));
    }
}
