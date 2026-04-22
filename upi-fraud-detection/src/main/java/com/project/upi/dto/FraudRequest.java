package com.project.upi.dto;

import java.util.List;

public class FraudRequest {
    private List<Double> features;

    public List<Double> getFeatures() {
        return features;
    }

    public void setFeatures(List<Double> features) {
        this.features = features;
    }
}