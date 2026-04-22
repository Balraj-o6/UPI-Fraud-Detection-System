package com.project.upi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class FraudService {

    public Map<String, Object> checkFraud(List<Double> features) {
        
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("features", features);

        String url = "http://127.0.0.1:5000/predict";

        Map response = restTemplate.postForObject(url, request, Map.class);

        return response;
    }
}