package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingSummary {

    @JsonProperty(value = "meeting_reference")
    private Reference meetingReference;
    @JsonProperty(value = "date")
    private Date date;
    @JsonProperty(value = "status")
    private String status;
    @JsonProperty(value = "course")
    private Course course;
    @JsonProperty(value = "going")
    private String going;
    @JsonProperty(value = "surface_summary")
    private String surface;
    @JsonProperty(value = "visible")
    private Boolean visible;
    @JsonProperty(value = "abandoned")
    private String abandoned;
    @JsonProperty(value = "weather")
    private String weather;

    public Reference getMeetingReference() {
        return meetingReference;
    }

    public void setMeetingReference(Reference meetingReference) {
        this.meetingReference = meetingReference;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getAbandoned() {
        return abandoned;
    }

    public void setAbandoned(String abandoned) {
        this.abandoned = abandoned;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
