package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HorsePreviousWinner {

    @JsonProperty(value = "horse_reference")
    private Reference horseReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "slug")
    private String slug;
    @JsonProperty(value = "age")
    private Double age;
    @JsonProperty(value = "previous_results")
    private List<Result> previousResults;
    @JsonProperty(value = "future_entries")
    private List<String> futureEntries;

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public List<Result> getPreviousResults() {
        return previousResults;
    }

    public void setPreviousResults(List<Result> previousResults) {
        this.previousResults = previousResults;
    }

    public List<String> getFutureEntries() {
        return futureEntries;
    }

    public void setFutureEntries(List<String> futureEntries) {
        this.futureEntries = futureEntries;
    }
}
