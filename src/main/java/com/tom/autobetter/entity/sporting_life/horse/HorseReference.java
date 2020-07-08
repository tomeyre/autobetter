package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HorseReference {

    @JsonProperty(value = "horse_reference")
    private Reference horseReference;
    @JsonProperty(value = "name")
    private String name;

    public Reference getHorseReference() {
        return horseReference;
    }

    public void setHorseReference(Reference horseReference) {
        this.horseReference = horseReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
