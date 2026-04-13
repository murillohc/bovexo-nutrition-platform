package com.bovexo.nutrition_analysis_service.controller;

import com.bovexo.nutrition_analysis_service.entity.NutritionAnalysis;
import com.bovexo.nutrition_analysis_service.service.NutritionAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NutritionAnalysisController {

    @Autowired
    NutritionAnalysisService service;

    @GetMapping("/analysis")
    public ResponseEntity<List<NutritionAnalysis>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/analysis/{animalId}")
    public ResponseEntity<List<NutritionAnalysis>>
    findByAnimalId(@PathVariable Long animalId) {
        return ResponseEntity.ok(service.findByAnimalId(animalId));
    }

}
