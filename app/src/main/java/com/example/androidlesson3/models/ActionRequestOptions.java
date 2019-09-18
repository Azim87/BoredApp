package com.example.androidlesson3.models;

public class ActionRequestOptions {

    private String type;
    private Float minPrice;
    private Float maxPrice;
    private Float minAccessibility;
    private Float maxAccessibility;

    public ActionRequestOptions(String type, Float minPrice, Float maxPrice, Float minAccessibility, Float maxAccessibility) {
        this.type = type;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minAccessibility = minAccessibility;
        this.maxAccessibility = maxAccessibility;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Float getMinAccessibility() {
        return minAccessibility;
    }

    public void setMinAccessibility(Float minAccessibility) {
        this.minAccessibility = minAccessibility;
    }

    public Float getMaxAccessibility() {
        return maxAccessibility;
    }

    public void setMaxAccessibility(Float maxAccessibility) {
        this.maxAccessibility = maxAccessibility;
    }
}

