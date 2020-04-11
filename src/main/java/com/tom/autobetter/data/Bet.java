package com.tom.autobetter.data;

public class Bet {

    private Long eventDate;
    private Integer eventId;
    private String eventName;
    private Integer raceId;
    private String race;
    private String horseName;
    private Boolean correct;
    private Double odds;
    private String time;
    private Long betId;

    public Bet(Long eventDate, Integer eventId, String eventName, Integer raceId, String race, String horseName, Boolean correct, Double odds, String time, Long betId) {
        this.eventDate = eventDate;
        this.eventId = eventId;
        this.eventName = eventName;
        this.raceId = raceId;
        this.race = race;
        this.horseName = horseName;
        this.correct = correct;
        this.odds = odds;
        this.time = time;
        this.betId = betId;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getBetId() {
        return betId;
    }

    public void setBetId(Long betId) {
        this.betId = betId;
    }
}
