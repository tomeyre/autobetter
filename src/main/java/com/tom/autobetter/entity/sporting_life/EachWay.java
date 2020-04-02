package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EachWay {

    @JsonProperty(value = "places")
    private Integer places;
    @JsonProperty(value = "numerator")
    private Integer numerator;
    @JsonProperty(value = "denominator")
    private Integer denominator;
}
