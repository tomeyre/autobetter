package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Score {

    @JsonProperty(value = "team")
    private Team team;
    @JsonProperty(value = "score")
    private List<ScoreResult> score;
    @JsonProperty(value = "penalty_shootout_score")
    private Integer penaltyShootoutScore;
    @JsonProperty(value = "aggregate_score")
    private Integer aggregate_score;

    public Integer getPenaltyShootoutScore() {
        return penaltyShootoutScore;
    }

    public void setPenaltyShootoutScore(Integer penaltyShootoutScore) {
        this.penaltyShootoutScore = penaltyShootoutScore;
    }

    public Integer getAggregate_score() {
        return aggregate_score;
    }

    public void setAggregate_score(Integer aggregate_score) {
        this.aggregate_score = aggregate_score;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<ScoreResult> getScore() {
        return score;
    }

    public void setScore(List<ScoreResult> score) {
        this.score = score;
    }
}
