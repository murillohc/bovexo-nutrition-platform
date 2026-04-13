package com.bovexo.nutrition_analysis_service.service;

import com.bovexo.nutrition_analysis_service.dto.FeedCostResponse;
import com.bovexo.nutrition_analysis_service.dto.FeedEventDto;
import com.bovexo.nutrition_analysis_service.entity.NutritionAnalysis;
import com.bovexo.nutrition_analysis_service.repository.NutritionAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NutritionAnalysisService {

    @Autowired
    NutritionAnalysisRepository nutritionAnalysisRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${feed.cost.service.url}")
    private String feedCostServiceUrl;


    public void processAnalysis(FeedEventDto event) {
        String url = feedCostServiceUrl + "/cost/" +
                event.feedType();
        FeedCostResponse costResponse = restTemplate.getForObject(url,
                FeedCostResponse.class);

        BigDecimal totalCost = costResponse.costPerKg()
                .multiply(BigDecimal.valueOf(event.quantity()))
                .setScale(2, RoundingMode.HALF_UP);

        NutritionAnalysis analysis = new NutritionAnalysis();
        analysis.setAnimalId(event.animalId());
        analysis.setFeedType(event.feedType());
        analysis.setQuantity(event.quantity());
        analysis.setCostPerKg(costResponse.costPerKg());
        analysis.setTotalCost(totalCost);
        analysis.setAnalysisDate(LocalDateTime.now());

        nutritionAnalysisRepository.save(analysis);
    }

    public List<NutritionAnalysis> findAll() {
        return nutritionAnalysisRepository.findAll();
    }

    public List<NutritionAnalysis> findByAnimalId(Long animalId) {
        List<NutritionAnalysis> list =
                nutritionAnalysisRepository.findByAnimalId(animalId);
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No analysis found for animal: " + animalId);
        }
        return list;
    }



}
