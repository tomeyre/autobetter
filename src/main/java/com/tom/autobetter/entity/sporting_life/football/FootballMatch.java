package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FootballMatch {

    @JsonProperty(value = "match_reference")
    private Reference matchReference;
    @JsonProperty(value = "match_date")
    private Date matchDate;
    @JsonProperty(value = "match_time")
    private String matchTime;
    @JsonProperty(value = "state")
    private String state;
    @JsonProperty(value = "link")
    private List<String> links;
    @JsonProperty(value = "match_type")
    private String matchType;
    @JsonProperty(value = "competition")
    private Competition competition;
    @JsonProperty(value = "round")
    private Round round;
    @JsonProperty(value = "legs")
    private Integer legs;
    @JsonProperty(value = "leg")
    private Integer leg;
    @JsonProperty(value = "team_score_a")
    private Score teamScoreA;
    @JsonProperty(value = "team_score_b")
    private Score teamScoreB;
    @JsonProperty(value = "lineup_a")
    private List<String> lineupA;
    @JsonProperty(value = "lineup_b")
    private List<String> lineupB;
    @JsonProperty(value = "match_outcome")
    private MatchOutcome matchOutcome;
    @JsonProperty(value = "clock")
    private String clock;
    @JsonProperty(value = "half_time_score")
    private MatchScore halfTimeScore;
    @JsonProperty(value = "full_time_score")
    private MatchScore fullTimeScore;
    @JsonProperty(value = "extra_time_score")
    private MatchScore extraTimeScore;
    @JsonProperty(value = "penalty_shootout_score")
    private MatchScore penaltyShootoutScore;
    @JsonProperty(value = "match_betting_market")
    private MatchBettingMarket matchBettingMarket;

    public MatchScore getExtraTimeScore() {
        return extraTimeScore;
    }

    public void setExtraTimeScore(MatchScore extraTimeScore) {
        this.extraTimeScore = extraTimeScore;
    }

    public MatchScore getPenaltyShootoutScore() {
        return penaltyShootoutScore;
    }

    public void setPenaltyShootoutScore(MatchScore penaltyShootoutScore) {
        this.penaltyShootoutScore = penaltyShootoutScore;
    }

    public MatchBettingMarket getMatchBettingMarket() {
        return matchBettingMarket;
    }

    public void setMatchBettingMarket(MatchBettingMarket matchBettingMarket) {
        this.matchBettingMarket = matchBettingMarket;
    }

    public Reference getMatchReference() {
        return matchReference;
    }

    public void setMatchReference(Reference matchReference) {
        this.matchReference = matchReference;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Integer getLegs() {
        return legs;
    }

    public void setLegs(Integer legs) {
        this.legs = legs;
    }

    public Integer getLeg() {
        return leg;
    }

    public void setLeg(Integer leg) {
        this.leg = leg;
    }

    public Score getTeamScoreA() {
        return teamScoreA;
    }

    public void setTeamScoreA(Score teamScoreA) {
        this.teamScoreA = teamScoreA;
    }

    public Score getTeamScoreB() {
        return teamScoreB;
    }

    public void setTeamScoreB(Score teamScoreB) {
        this.teamScoreB = teamScoreB;
    }

    public List<String> getLineupA() {
        return lineupA;
    }

    public void setLineupA(List<String> lineupA) {
        this.lineupA = lineupA;
    }

    public List<String> getLineupB() {
        return lineupB;
    }

    public void setLineupB(List<String> lineupB) {
        this.lineupB = lineupB;
    }

    public MatchOutcome getMatchOutcome() {
        return matchOutcome;
    }

    public void setMatchOutcome(MatchOutcome matchOutcome) {
        this.matchOutcome = matchOutcome;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

    public MatchScore getHalfTimeScore() {
        return halfTimeScore;
    }

    public void setHalfTimeScore(MatchScore halfTimeScore) {
        this.halfTimeScore = halfTimeScore;
    }

    public MatchScore getFullTimeScore() {
        return fullTimeScore;
    }

    public void setFullTimeScore(MatchScore fullTimeScore) {
        this.fullTimeScore = fullTimeScore;
    }
}
