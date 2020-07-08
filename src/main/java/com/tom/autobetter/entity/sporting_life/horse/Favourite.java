package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Favourite {

    @JsonProperty(value = "betting_favourite")
    private String bettingFavourite;

    public String getBettingFavourite() {
        return bettingFavourite;
    }

    public void setBettingFavourite(String bettingFavourite) {
        this.bettingFavourite = bettingFavourite;
    }
}
