package com.aiary.aiary.domain.diary.entity;

public enum Weather {

    SUNNY("맑음"),
    Cloudy("흐림"),
    RAIN("비"),
    SNOW("눈");

    private final String type;

    Weather(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
