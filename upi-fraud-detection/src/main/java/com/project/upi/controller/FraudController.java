package com.project.upi.controller;

import com.project.upi.dto.FraudRequest;
import com.project.upi.entity.Transaction;
import com.project.upi.repository.TransactionRepository;
import com.project.upi.service.FraudService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api")
public class FraudController {

    private final FraudService fraudService;
    private final TransactionRepository repo;

    public FraudController(FraudService fraudService, TransactionRepository repo) {
        this.fraudService = fraudService;
        this.repo = repo;
    }

    @PostMapping("/check")
    public Map<String, Object> check(@RequestBody FraudRequest request) {

        List<Double> features = request.getFeatures();

        if (features.size() != 30) {
            throw new RuntimeException("Exactly 30 features required");
        }

        Map<String, Object> result = fraudService.checkFraud(features);

        Transaction t = new Transaction();
        t.setFeatures(features.toString());
        t.setFraud((Integer) result.get("fraud"));
        t.setProbability((Double) result.get("probability"));

        repo.save(t);

        return result;
    }
    
    @PostMapping("/check-url")
    public Map<String, Object> checkUrl(@RequestBody Map<String, String> body) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://127.0.0.1:5000/check_url";

        Map response = restTemplate.postForObject(url, body, Map.class);

        return response;
    }
}