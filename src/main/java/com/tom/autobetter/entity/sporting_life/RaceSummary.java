package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceSummary {

    @JsonProperty(value = "meeting_summary_reference")
    private Reference meetingSummaryReference;
    @JsonProperty(value = "race_summary_reference")
    private Reference raceSummaryReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "course_name")
    private String courseName;
    @JsonProperty(value = "course_shortcode")
    private String courseShortcode;
    @JsonProperty(value = "course_surface")
    private CourseSurface courseSurface;
    @JsonProperty(value = "age")
    private String age;
    @JsonProperty(value = "race_class")
    private Integer raceClass;
    @JsonProperty(value = "distance")
    private String distance;
    @JsonProperty(value = "date")
    private Date date;
    @JsonProperty(value = "time")
    private String time;
    @JsonProperty(value = "off_time")
    private String offTime;
    @JsonProperty(value = "winning_time")
    private String winningTime;
    @JsonProperty(value = "ride_count")
    private Integer rideCount;
    @JsonProperty(value = "race_stage")
    private String raceStage;
    @JsonProperty(value = "has_video")
    private Boolean hasVideo;
    @JsonProperty(value = "going")
    private String going;
    @JsonProperty(value = "going_shortcode")
    private String goingShortcode;
    @JsonProperty(value = "has_handicap")
    private Boolean hasHandicap;
    @JsonProperty(value = "verdict")
    private String verdict;
    @JsonProperty(value = "hidden")
    private Boolean hidden;
    @JsonProperty(value = "promotion")
    private Promotion promotion;

    public String getGoingShortcode() {
        return goingShortcode;
    }

    public void setGoingShortcode(String goingShortcode) {
        this.goingShortcode = goingShortcode;
    }

    public String getCourseShortcode() {
        return courseShortcode;
    }

    public void setCourseShortcode(String courseShortcode) {
        this.courseShortcode = courseShortcode;
    }

    private RaceDetails raceDetails = new RaceDetails();

    public RaceDetails getRaceDetails() {
        return raceDetails;
    }

    public void setRaceDetails(RaceDetails raceDetails) {
        this.raceDetails = raceDetails;
    }

    public Reference getMeetingSummaryReference() {
        return meetingSummaryReference;
    }

    public void setMeetingSummaryReference(Reference meetingSummaryReference) {
        this.meetingSummaryReference = meetingSummaryReference;
    }

    public Reference getRaceSummaryReference() {
        return raceSummaryReference;
    }

    public void setRaceSummaryReference(Reference raceSummaryReference) {
        this.raceSummaryReference = raceSummaryReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseSurface getCourseSurface() {
        return courseSurface;
    }

    public void setCourseSurface(CourseSurface courseSurface) {
        this.courseSurface = courseSurface;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(Integer raceClass) {
        this.raceClass = raceClass;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public String getWinningTime() {
        return winningTime;
    }

    public void setWinningTime(String winningTime) {
        this.winningTime = winningTime;
    }

    public Integer getRideCount() {
        return rideCount;
    }

    public void setRideCount(Integer rideCount) {
        this.rideCount = rideCount;
    }

    public String getRaceStage() {
        return raceStage;
    }

    public void setRaceStage(String raceStage) {
        this.raceStage = raceStage;
    }

    public Boolean getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(Boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public Boolean getHasHandicap() {
        return hasHandicap;
    }

    public void setHasHandicap(Boolean hasHandicap) {
        this.hasHandicap = hasHandicap;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
