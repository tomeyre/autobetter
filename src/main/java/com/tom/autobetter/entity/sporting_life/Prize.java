package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Prize {

    @JsonProperty(value = "position")
    private Integer positionFinished;
    @JsonProperty(value = "prize")
    private Double prize;

    public Integer getPositionFinished() {
        return positionFinished;
    }

    public void setPositionFinished(Integer positionFinished) {
        this.positionFinished = positionFinished;
    }

    public Double getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = Double.parseDouble(prize.split("")[0]);
    }
}
