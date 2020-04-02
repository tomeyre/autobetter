package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Jockey {

    @JsonProperty(value = "person_reference")
    private Reference jockeyReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "slug")
    private String slug;

    public Reference getJockeyReference() {
        return jockeyReference;
    }

    public void setJockeyReference(Reference jockeyReference) {
        this.jockeyReference = jockeyReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
