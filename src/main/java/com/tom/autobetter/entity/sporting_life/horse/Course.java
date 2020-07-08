package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {

    @JsonProperty(value = "course_reference")
    private Reference courseReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "country")
    private Country country;
    @JsonProperty(value = "feed_source")
    private String feedSource;

    public Reference getCourseReference() {
        return courseReference;
    }

    public void setCourseReference(Reference courseReference) {
        this.courseReference = courseReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getFeedSource() {
        return feedSource;
    }

    public void setFeedSource(String feedSource) {
        this.feedSource = feedSource;
    }
}
