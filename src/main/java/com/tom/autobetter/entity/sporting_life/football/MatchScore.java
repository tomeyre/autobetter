package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchScore {

    @JsonProperty(value = "home")
    private Integer home;
    @JsonProperty(value = "away")
    private Integer away;

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getAway() {
        return away;
    }

    public void setAway(Integer away) {
        this.away = away;
    }
}
