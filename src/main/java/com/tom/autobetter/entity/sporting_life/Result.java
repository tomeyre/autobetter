package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Result {

    @JsonProperty(value = "race_id")
    private Integer raceId;
    @JsonProperty(value = "date")
    private Calendar date;
    @JsonProperty(value = "time")
    private Calendar time;
    @JsonProperty(value = "course_name")
    private String courseName;
    @JsonProperty(value = "distance")
    private String distance;
    @JsonProperty(value = "run_type")
    private String runType;
    @JsonProperty(value = "weight")
    private String weight;
    @JsonProperty(value = "position")
    private Integer position;
    @JsonProperty(value = "amended_position")
    private Integer amendedPosition;
    @JsonProperty(value = "casualty")
    private Casualty casualty;
    @JsonProperty(value = "runner_count")
    private Integer runnerCount;
    @JsonProperty(value = "bha")
    private Integer bha;
    @JsonProperty(value = "race_class")
    private Integer raceClass;
    @JsonProperty(value = "race_name")
    private String raceName;
    @JsonProperty(value = "going")
    private String going;
    @JsonProperty(value = "odds")
    private String odds;
    @JsonProperty(value = "ride_description")
    private String rideDescrition;
    @JsonProperty(value = "video")
    private Video video;

    public Integer getAmendedPosition() {
        return amendedPosition;
    }

    public void setAmendedPosition(Integer amendedPosition) {
        this.amendedPosition = amendedPosition;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date temp = simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(temp);
            this.date = calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        try {
            Date temp = simpleDateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(temp);
            this.date = calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }

    public Integer getRunnerCount() {
        return runnerCount;
    }

    public void setRunnerCount(Integer runnerCount) {
        this.runnerCount = runnerCount;
    }

    public Integer getBha() {
        return bha;
    }

    public void setBha(Integer bha) {
        this.bha = bha;
    }

    public Integer getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(Integer raceClass) {
        this.raceClass = raceClass;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getRideDescrition() {
        return rideDescrition;
    }

    public void setRideDescrition(String rideDescrition) {
        this.rideDescrition = rideDescrition;
    }

    public Video getVideos() {
        return video;
    }

    public void setVideos(Video video) {
        this.video = video;
    }
}
