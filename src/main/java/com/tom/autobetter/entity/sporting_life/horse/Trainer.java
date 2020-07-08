package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trainer  extends JockeyTrainerDetails{

    @JsonProperty(value = "business_reference")
    private Reference trainerReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "slug")
    private String slug;

    public Reference getTrainerReference() {
        return trainerReference;
    }

    public void setTrainerReference(Reference trainerReference) {
        this.trainerReference = trainerReference;
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
