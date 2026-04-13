package com.bovexo.nutrition_analysis_service.consumers;

import com.bovexo.nutrition_analysis_service.dto.FeedEventDto;
import com.bovexo.nutrition_analysis_service.entity.NutritionAnalysis;
import com.bovexo.nutrition_analysis_service.service.NutritionAnalysisService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FeedConsumer {

    @Autowired
    NutritionAnalysisService nutritionAnalysisService;

    @RabbitListener(queues = "${broker.queue.feeds.name}")
    public void listenFeedQueue(@Payload FeedEventDto feedEventDto){
        System.out.println(">>> Mensagem recebida: " + feedEventDto);
        nutritionAnalysisService.processAnalysis(feedEventDto);
    }
}
