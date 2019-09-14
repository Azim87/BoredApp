package com.example.androidlesson3.models;

import com.google.gson.annotations.SerializedName;

public enum EnamActionType {

    @SerializedName("education")
    EDUCATION,

    @SerializedName("recreational")
    RECREATIONAL,

    @SerializedName("social")
    SOCIAL,

    @SerializedName("diy")
    DIY,

    @SerializedName("charity")
    CHARITY,

    @SerializedName("cooking")
    COOKING,

    @SerializedName("relaxation")
    RELAXATION,

    @SerializedName("music")
    MUSIC,

    @SerializedName("busywork")
    BUSYWORK
}
