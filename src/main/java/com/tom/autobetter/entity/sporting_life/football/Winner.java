package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Winner {

    @JsonProperty(value = "team_reference")
    private Reference teamReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "short_name")
    private String shortName;

    public Reference getTeamReference() {
        return teamReference;
    }

    public void setTeamReference(Reference teamReference) {
        this.teamReference = teamReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
