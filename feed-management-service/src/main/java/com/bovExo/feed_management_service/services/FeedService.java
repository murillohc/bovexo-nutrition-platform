package com.bovExo.feed_management_service.services;

import com.bovExo.feed_management_service.entity.Feed;
import com.bovExo.feed_management_service.producers.FeedProducer;
import com.bovExo.feed_management_service.repository.FeedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FeedService {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    FeedProducer feedProducer;

    @Transactional
    public Feed create(Feed feed){
        feed = feedRepository.save(feed);
        feedProducer.publishMessageFeed(feed);
        return feed;
    }

    @Transactional
    public List<Feed> findAll(){
        return feedRepository.findAll();
    }

    public List<Feed> findByAnimalId(Long animalId){
        List<Feed> feeds = feedRepository.findByAnimalId(animalId);
        if (feeds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "\n" +
                    "No records found for the animal: " + animalId);
        }
        return feeds;
    }
}
