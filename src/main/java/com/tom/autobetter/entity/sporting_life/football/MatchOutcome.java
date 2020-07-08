package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchOutcome {

    @JsonProperty(value = "outcome")
    private String outcome;
    @JsonProperty(value = "result_type")
    private String resultType;
    @JsonProperty(value = "winner")
    private Winner winner;

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }
}
