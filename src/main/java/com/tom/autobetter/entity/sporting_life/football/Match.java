package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Match {

    @JsonProperty(value = "team_a")
    private TeamResult teamA;
    @JsonProperty(value = "team_a")
    private TeamResult teamB;

    public TeamResult getTeamA() {
        return teamA;
    }

    public void setTeamA(TeamResult teamA) {
        this.teamA = teamA;
    }

    public TeamResult getTeamB() {
        return teamB;
    }

    public void setTeamB(TeamResult teamB) {
        this.teamB = teamB;
    }
}
