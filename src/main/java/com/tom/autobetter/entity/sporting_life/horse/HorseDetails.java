package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HorseDetails {

    @JsonProperty(value = "horse_reference")
    private Reference horseReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "slug")
    private String slug;
    @JsonProperty(value = "last_ran_days")
    private Integer lastRanDays;
    @JsonProperty(value = "age")
    private Integer age;
    @JsonProperty(value = "foaled")
    private String foaled;
    @JsonProperty(value = "sex")
    private Sex sex;
    @JsonProperty(value = "formsummary")
    private FormSummary formSummary;
    @JsonProperty(value = "previous_results")
    private List<Result> previousResults;
    @JsonProperty(value = "future_entries")
    private List<String> futureEntries;
    @JsonProperty(value = "last_run_video")
    private Video lastRunVideo;

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

    public Integer getLastRanDays() {
        return lastRanDays;
    }

    public void setLastRanDays(Integer lastRanDays) {
        this.lastRanDays = lastRanDays;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFoaled() {
        return foaled;
    }

    public void setFoaled(String foaled) {
        this.foaled = foaled;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public FormSummary getFormSummary() {
        return formSummary;
    }

    public void setFormSummary(FormSummary formSummary) {
        this.formSummary = formSummary;
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

    public Video getLastRunVideos() {
        return lastRunVideo;
    }

    public void setLastRunVideos(Video lastRunVideo) {
        this.lastRunVideo = lastRunVideo;
    }
}
