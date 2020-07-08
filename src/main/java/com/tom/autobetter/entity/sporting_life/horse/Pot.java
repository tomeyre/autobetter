package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pot {

    @JsonProperty(value = "pot")
    private Double pot;
    @JsonProperty(value = "pool")
    private Double pool;
    @JsonProperty(value = "winStakes")
    private Double winStakes;

    public Double getPot() {
        return pot;
    }

    public void setPot(String pot) {
        this.pot = Double.parseDouble(pot.split("")[0]);
    }

    public Double getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = Double.parseDouble(pool.split("")[0]);
    }

    public Double getWinStakes() {
        return winStakes;
    }

    public void setWinStakes(Double winStakes) {
        this.winStakes = winStakes;
    }
}
