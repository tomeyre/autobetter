package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchBet {

    @JsonProperty(value = "bet_reference")
    private Reference betReference;
    @JsonProperty(value = "market_reference")
    private Reference marketReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "odds")
    private String odds;
    @JsonProperty(value = "has_sp")
    private Boolean hasSp;

    public Reference getBetReference() {
        return betReference;
    }

    public void setBetReference(Reference betReference) {
        this.betReference = betReference;
    }

    public Reference getMarketReference() {
        return marketReference;
    }

    public void setMarketReference(Reference marketReference) {
        this.marketReference = marketReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Boolean getHasSp() {
        return hasSp;
    }

    public void setHasSp(Boolean hasSp) {
        this.hasSp = hasSp;
    }
}
