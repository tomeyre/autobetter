package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaceDetails {

    @JsonProperty(value = "race_summary")
    private RaceSummary raceSummary;
    @JsonProperty(value = "prizes")
    private Prizes prizes;
    @JsonProperty(value = "betting_forecast")
    private String bettingForecast;
    @JsonProperty(value = "rides")
    private List<Horse> horses;
    @JsonProperty(value = "number_of_placed_rides")
    private Integer numberOfPlacedHorses;
    @JsonProperty(value = "each_way")
    private EachWay eachWay;
    @JsonProperty(value = "videos")
    private List<Video> videos;
    @JsonProperty(value = "tote_win")
    private Double toteWin;
    @JsonProperty(value = "place_win")
    private String placeWin;
    @JsonProperty(value = "exacta_win")
    private Double exactaWin;
    @JsonProperty(value = "trifecta")
    private Double trifecta;
    @JsonProperty(value = "tricast")
    private Double tricast;
    @JsonProperty(value = "swingers")
    private String swingers;
    @JsonProperty(value = "straight_forecast")
    private Double straightForecast;
    @JsonProperty(value = "place_pot")
    private Pot placePot;
    @JsonProperty(value = "quad_pot")
    private Pot quadPot;
    @JsonProperty(value = "on_course_book_percentage")
    private Double onCourseBookPercentage;
    @JsonProperty(value = "market_suspended")
    private Boolean marketSuspended;
    @JsonProperty(value = "fixture")
    private String fixture;
    @JsonProperty(value = "deductions")
    private List<History> deductions;
    @JsonProperty(value = "stewards")
    private String stewards;
    @JsonProperty(value = "last_years_winners")
    private List<Winners> lastYearsWinners;

    public RaceSummary getRaceSummary() {
        return raceSummary;
    }

    public void setRaceSummary(RaceSummary raceSummary) {
        this.raceSummary = raceSummary;
    }

    public Prizes getPrizes() {
        return prizes;
    }

    public void setPrizes(Prizes prizes) {
        this.prizes = prizes;
    }

    public String getBettingForecast() {
        return bettingForecast;
    }

    public void setBettingForecast(String bettingForecast) {
        this.bettingForecast = bettingForecast;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public void setHorses(List<Horse> horses) {
        this.horses = horses;
    }

    public Integer getNumberOfPlacedHorses() {
        return numberOfPlacedHorses;
    }

    public void setNumberOfPlacedHorses(Integer numberOfPlacedHorses) {
        this.numberOfPlacedHorses = numberOfPlacedHorses;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public Double getToteWin() {
        return toteWin;
    }

    public void setToteWin(String toteWin) {
        this.toteWin = Double.parseDouble(toteWin.split("")[0]);
    }

    public String getPlaceWin() {
        return placeWin;
    }

    public void setPlaceWin(String placeWin) {
        this.placeWin = placeWin;
    }

    public Double getExactaWin() {
        return exactaWin;
    }

    public void setExactaWin(String exactaWin) {
        this.exactaWin = Double.parseDouble(exactaWin.split("")[0]);
    }

    public Double getTrifecta() {
        return trifecta;
    }

    public void setTrifecta(String trifecta) {
        this.trifecta = Double.parseDouble(trifecta.split("")[0]);
    }

    public Double getTricast() {
        return tricast;
    }

    public void setTricast(String tricast) {
        this.tricast = Double.parseDouble(tricast.split("")[0]);
    }

    public String getSwingers() {
        return swingers;
    }

    public void setSwingers(String swingers) {
        this.swingers = swingers;
    }

    public Double getStraightForecast() {
        return straightForecast;
    }

    public void setStraightForecast(String straightForecast) {
        this.straightForecast = Double.parseDouble(straightForecast.split("")[0]);
    }

    public Pot getPlacePot() {
        return placePot;
    }

    public void setPlacePot(Pot placePot) {
        this.placePot = placePot;
    }

    public Pot getQuadPot() {
        return quadPot;
    }

    public void setQuadPot(Pot quadPot) {
        this.quadPot = quadPot;
    }

    public Double getOnCourseBookPercentage() {
        return onCourseBookPercentage;
    }

    public void setOnCourseBookPercentage(Double onCourseBookPercentage) {
        this.onCourseBookPercentage = onCourseBookPercentage;
    }

    public List<History> getDeductions() {
        return deductions;
    }

    public void setDeductions(List<History> deductions) {
        this.deductions = deductions;
    }

    public String getStewards() {
        return stewards;
    }

    public void setStewards(String stewards) {
        this.stewards = stewards;
    }

    public List<Winners> getLastYearsWinners() {
        return lastYearsWinners;
    }

    public void setLastYearsWinners(List<Winners> lastYearsWinners) {
        this.lastYearsWinners = lastYearsWinners;
    }
}
