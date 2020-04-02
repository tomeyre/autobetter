package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MarketReference {

    @JsonProperty(value = "external_reference")
    private List<ExternalReference> externalReference;
}
