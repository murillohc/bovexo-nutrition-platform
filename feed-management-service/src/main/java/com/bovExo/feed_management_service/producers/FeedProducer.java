package com.bovExo.feed_management_service.producers;

import com.bovExo.feed_management_service.dtos.FeedRecord;
import com.bovExo.feed_management_service.entity.Feed;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeedProducer {

    final RabbitTemplate rabbitTemplate;


    public FeedProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.feeds.name}")
    private String routingKey;

    public void publishMessageFeed(Feed feed){
        var feedRecord = new FeedRecord(
                feed.getAnimalId(),
                feed.getFeedType(),
                feed.getQuantity()
        );

    rabbitTemplate.convertAndSend("", routingKey, feedRecord);
    }
}
