package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class History {

    @JsonProperty(value = "type")
    private String raceType;
    @JsonProperty(value = "value")
    private String value;

}
