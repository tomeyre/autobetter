package com.tom.autobetter.entity.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class WinPercentage {
    private String race;
    private Double winPercentage;

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Double getWinPercentage() {
        return winPercentage;
    }

    public void setWinPercentage(Double winPercentage) {
        this.winPercentage = winPercentage;
    }
}
