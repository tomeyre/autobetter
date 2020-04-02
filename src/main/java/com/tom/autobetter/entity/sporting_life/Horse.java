package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Horse {

    @JsonProperty(value = "ride_reference")
    private Reference horseReference;
    @JsonProperty(value = "cloth_number")
    private Integer clothNumber;
    @JsonProperty(value = "draw_number")
    private Integer drawNumber;
    @JsonProperty(value = "finish_position")
    private Integer finishingPosition;
    @JsonProperty(value = "amended_position")
    private Integer amendedPosition;
    @JsonProperty(value = "finish_distance")
    private String finishDistance;
    @JsonProperty(value = "ride_status")
    private Boolean running;
    @JsonProperty(value = "horse")
    private HorseDetails horseDetails;
    @JsonProperty(value = "handicap")
    private String handicap;
    @JsonProperty(value = "weight_penalty")
    private Integer weightPenalty;
    @JsonProperty(value = "headgear")
    private List<Headgear> headgear;
    @JsonProperty(value = "official_rating")
    private Integer officialRating;
    @JsonProperty(value = "jockey_claim")
    private Integer jockeyClaim;
    @JsonProperty(value = "owner")
    private Owner owner;
    @JsonProperty(value = "trainer")
    private Trainer trainer;
    @JsonProperty(value = "jockey")
    private Jockey jockey;
    @JsonProperty(value = "silk_filename")
    private String silkFileName;
    @JsonProperty(value = "casualty")
    private Casualty casualty;
    @JsonProperty(value = "ride_description")
    private String rideDescription;
    @JsonProperty(value = "betting")
    private Betting betting;
    @JsonProperty(value = "commentary")
    private String commentary;
    @JsonProperty(value = "bet_movements")
    private String betMovements;
    @JsonProperty(value = "race_history_stats")
    private List<History> raceHistoryStats;
    @JsonProperty(value = "has_sp")
    private Boolean hasSp;
    @JsonProperty(value = "medication")
    private List<Medication> medication;
    @JsonProperty(value = "insights")
    private List<Insight> insights;

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public Integer getJockeyClaim() {
        return jockeyClaim;
    }

    public void setJockeyClaim(Integer jockeyClaim) {
        this.jockeyClaim = jockeyClaim;
    }

    public String getFinishDistance() {
        return finishDistance;
    }

    public void setFinishDistance(String finishDistance) {
        this.finishDistance = finishDistance;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Reference getHorseReference() {
        return horseReference;
    }

    public void setHorseReference(Reference horseReference) {
        this.horseReference = horseReference;
    }

    public Integer getClothNumber() {
        return clothNumber;
    }

    public void setClothNumber(Integer clothNumber) {
        this.clothNumber = clothNumber;
    }

    public Integer getFinishingPosition() {
        return finishingPosition;
    }

    public void setFinishingPosition(Integer finishingPosition) {
        this.finishingPosition = finishingPosition;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running.equalsIgnoreCase("running");
    }

    public HorseDetails getHorseDetails() {
        return horseDetails;
    }

    public void setHorseDetails(HorseDetails horseDetails) {
        this.horseDetails = horseDetails;
    }

    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }

    public List<Headgear> getHeadgear() {
        return headgear;
    }

    public void setHeadgear(List<Headgear> headgear) {
        this.headgear = headgear;
    }

    public Integer getOfficialRating() {
        return officialRating;
    }

    public void setOfficialRating(Integer officialRating) {
        this.officialRating = officialRating;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public String getSilkFileName() {
        return silkFileName;
    }

    public void setSilkFileName(String silkFileName) {
        this.silkFileName = silkFileName;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }

    public String getRideDescription() {
        return rideDescription;
    }

    public void setRideDescription(String rideDescription) {
        this.rideDescription = rideDescription;
    }

    public Betting getBetting() {
        return betting;
    }

    public void setBetting(Betting betting) {
        this.betting = betting;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getBetMovements() {
        return betMovements;
    }

    public void setBetMovements(String betMovements) {
        this.betMovements = betMovements;
    }

    public List<History> getRaceHistoryStats() {
        return raceHistoryStats;
    }

    public void setRaceHistoryStats(List<History> raceHistoryStats) {
        this.raceHistoryStats = raceHistoryStats;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
    }

    public List<Insight> getInsights() {
        return insights;
    }

    public void setInsights(List<Insight> insights) {
        this.insights = insights;
    }
}
