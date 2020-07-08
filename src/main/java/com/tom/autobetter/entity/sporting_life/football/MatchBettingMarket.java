package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class MatchBettingMarket {

    @JsonProperty(value = "live")
    private Boolean live;
    @JsonProperty(value = "match_betting")
    private List<MatchBet> matchBetting;
    @JsonProperty(value = "market_suspended")
    private Boolean marketSuspended;
    @JsonProperty(value = "fixture")
    private String fixture;

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public List<MatchBet> getMatchBetting() {
        return matchBetting;
    }

    public void setMatchBetting(List<MatchBet> matchBetting) {
        this.matchBetting = matchBetting;
    }

    public Boolean getMarketSuspended() {
        return marketSuspended;
    }

    public void setMarketSuspended(Boolean marketSuspended) {
        this.marketSuspended = marketSuspended;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }
}
