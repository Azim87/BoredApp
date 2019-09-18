package com.example.androidlesson3.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class BoredAction {
    @SerializedName("activity")
    private String activity;

    @SerializedName("accessibility")
    private Float accessibility;

    @SerializedName("type")
    private EnamActionType type;

    @SerializedName("participants")
    private Integer participants;

    @SerializedName("price")
    private Float price;

    @SerializedName("link")
    private String link;

    //region Constructor
    public BoredAction(String activity, Float accessibility, EnamActionType type, Integer participants, Float price, String link) {
        this.activity = activity;
        this.accessibility = accessibility;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.link = link;
    }
    //endregion

    //region Getter and Setters
    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Float getAccessibility() {
        return accessibility; }

    public void setAccessibility(Float accessibility) {
        this.accessibility = accessibility;
    }

    public EnamActionType getType() {
        return type;
    }

    public void setType(EnamActionType type) {
        this.type = type;
    }

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    //endregion

    //region toString
    @NonNull
    @Override
    public String toString() {
        return activity + " " + accessibility + " " + type + " " + participants + " " + price;
    }
    //endregion
}
