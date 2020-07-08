package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Round {
    @JsonProperty(value = "round_number")
    private Integer roundNumber;
    @JsonProperty(value = "round_type")
    private String roundType;

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }
}
