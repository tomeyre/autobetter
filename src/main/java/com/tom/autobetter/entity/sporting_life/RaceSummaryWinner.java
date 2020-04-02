package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RaceSummaryWinner {

    @JsonProperty(value = "race_summary_reference")
    private Reference raceSummaryReference;
    @JsonProperty(value = "name")
    private String name;

}
