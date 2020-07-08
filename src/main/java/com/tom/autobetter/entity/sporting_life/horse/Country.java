package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Country {

    @JsonProperty(value = "country_reference")
    private Reference countryReference;
    @JsonProperty(value = "long_name")
    private String country;

    public Reference getCountryReference() {
        return countryReference;
    }

    public void setCountryReference(Reference countryReference) {
        this.countryReference = countryReference;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
