package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonDetails {

    @JsonProperty(value = "person_reference")
    private Reference personReference;
    @JsonProperty(value = "name")
    private String name;

    public Reference getPersonReference() {
        return personReference;
    }

    public void setPersonReference(Reference personReference) {
        this.personReference = personReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
