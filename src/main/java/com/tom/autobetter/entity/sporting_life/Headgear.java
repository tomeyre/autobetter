package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Headgear {

    @JsonProperty(value = "symbol")
    private String symbol;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "count")
    private Integer count;
}
