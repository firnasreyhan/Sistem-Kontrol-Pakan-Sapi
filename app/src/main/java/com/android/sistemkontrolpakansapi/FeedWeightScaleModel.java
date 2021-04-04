package com.android.sistemkontrolpakansapi;

public class FeedWeightScaleModel {
    private String day;
    private String timestamp;
    private String weight;

    public FeedWeightScaleModel(String day, String timestamp, String weight) {
        this.day = day;
        this.timestamp = timestamp;
        this.weight = weight;
    }

    public String getDay() {
        return day;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getWeight() {
        return weight;
    }
}
