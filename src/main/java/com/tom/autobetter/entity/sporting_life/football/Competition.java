package com.tom.autobetter.entity.sporting_life.football;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Competition {

    @JsonProperty(value = "competition_reference")
    private Reference competitionReference;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "show_tables")
    private Boolean showTables;
    @JsonProperty(value = "table_key")
    private String showKey;
    @JsonProperty(value = "round")
    private List<String> round;
    @JsonProperty(value = "sort_order")
    private Integer sortOrder;
    @JsonProperty(value = "teams")
    private List<Team> teams;
    @JsonProperty(value = "season_start_date")
    private String seasonStartDate;
    @JsonProperty(value = "season_end_date")
    private String seasonEndDate;
    @JsonProperty(value = "show_teams_dropdown")
    private Boolean showTeamDropdown;

    public String getShowKey() {
        return showKey;
    }

    public void setShowKey(String showKey) {
        this.showKey = showKey;
    }

    public String getSeasonStartDate() {
        return seasonStartDate;
    }

    public void setSeasonStartDate(String seasonStartDate) {
        this.seasonStartDate = seasonStartDate;
    }

    public String getSeasonEndDate() {
        return seasonEndDate;
    }

    public void setSeasonEndDate(String seasonEndDate) {
        this.seasonEndDate = seasonEndDate;
    }

    public Boolean getShowTeamDropdown() {
        return showTeamDropdown;
    }

    public void setShowTeamDropdown(Boolean showTeamDropdown) {
        this.showTeamDropdown = showTeamDropdown;
    }

    public Boolean getShowTables() {
        return showTables;
    }

    public void setShowTables(Boolean showTables) {
        this.showTables = showTables;
    }

    public Reference getCompetitionReference() {
        return competitionReference;
    }

    public void setCompetitionReference(Reference competitionReference) {
        this.competitionReference = competitionReference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRound() {
        return round;
    }

    public void setRound(List<String> round) {
        this.round = round;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
