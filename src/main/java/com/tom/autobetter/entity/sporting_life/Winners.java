package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Winners {

    @JsonProperty(value = "ride_reference")
    private Reference rideReference;
    @JsonProperty(value = "race_summary")
    private RaceSummaryWinner raceSummary;
    @JsonProperty(value = "meeting_summary")
    private MeetingSummary meetingSummary;
    @JsonProperty(value = "draw_number")
    private Integer drawNumber;
    @JsonProperty(value = "horse")
    private HorsePreviousWinner horsePreviousWinner;
    @JsonProperty(value = "trainer")
    private Trainer trainer;
    @JsonProperty(value = "jockey")
    private Jockey jockey;
    @JsonProperty(value = "weight")
    private String weight;
    @JsonProperty(value = "odds")
    private String odds;

    public Reference getRideReference() {
        return rideReference;
    }

    public void setRideReference(Reference rideReference) {
        this.rideReference = rideReference;
    }

    public RaceSummaryWinner getRaceSummary() {
        return raceSummary;
    }

    public void setRaceSummary(RaceSummaryWinner raceSummary) {
        this.raceSummary = raceSummary;
    }

    public MeetingSummary getMeetingSummary() {
        return meetingSummary;
    }

    public void setMeetingSummary(MeetingSummary meetingSummary) {
        this.meetingSummary = meetingSummary;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public HorsePreviousWinner getHorsePreviousWinner() {
        return horsePreviousWinner;
    }

    public void setHorsePreviousWinner(HorsePreviousWinner horsePreviousWinner) {
        this.horsePreviousWinner = horsePreviousWinner;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
