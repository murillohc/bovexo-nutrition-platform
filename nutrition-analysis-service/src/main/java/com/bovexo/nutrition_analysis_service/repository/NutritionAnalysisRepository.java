package com.bovexo.nutrition_analysis_service.repository;

import com.bovexo.nutrition_analysis_service.entity.NutritionAnalysis;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NutritionAnalysisRepository extends MongoRepository<NutritionAnalysis, String> {

    List<NutritionAnalysis> findByAnimalId(Long animalId);
}
