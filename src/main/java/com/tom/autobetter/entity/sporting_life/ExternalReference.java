package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExternalReference {

    @JsonProperty(value = "ref")
    private String ref;
    @JsonProperty(value = "value")
    private String value;
}
