package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Betting {

    @JsonProperty(value = "market_reference")
    private MarketReference marketReference;
    @JsonProperty(value = "favourite")
    private Favourite favourite;
    @JsonProperty(value = "current_odds")
    private String currentOdds;
    @JsonProperty(value = "historical_odds")
    private List<String> historicOdds;

    public Favourite getFavourite() {
        return favourite;
    }

    public void setFavourite(Favourite favourite) {
        this.favourite = favourite;
    }

    public String getCurrentOdds() {
        return currentOdds;
    }

    public void setCurrentOdds(String currentOdds) {
        this.currentOdds = currentOdds;
    }

    public List<String> getHistoricOdds() {
        return historicOdds;
    }

    public void setHistoricOdds(List<String> historicOdds) {
        this.historicOdds = historicOdds;
    }
}
