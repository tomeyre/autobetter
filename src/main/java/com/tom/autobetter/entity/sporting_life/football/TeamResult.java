package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamResult {

    @JsonProperty(value = "team")
    private Team team;
    @JsonProperty(value = "matches")
    private List<FootballMatch> match = new ArrayList<>();

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<FootballMatch> getMatch() {
        return match;
    }

    public void setMatch(List<FootballMatch> match) {
        if(match != null) {
            this.match = match;
        }
    }
}
