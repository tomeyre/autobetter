package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JockeyTrainerDetails {

    @JsonProperty(value = "person_details")
    private PersonDetails personDetails;
    @JsonProperty(value = "run_previous_results")
    private List<JockeyTrainerResult> results;
    @JsonProperty(value = "run_future_entries")
    private List<JockeyTrainerResult> futureRaces;

    public List<JockeyTrainerResult> getFutureRaces() {
        return futureRaces;
    }

    public void setFutureRaces(List<JockeyTrainerResult> futureRaces) {
        this.futureRaces = futureRaces;
    }

    public PersonDetails getPersonDetails() {
        return personDetails;
    }

    public void setPersonDetails(PersonDetails personDetails) {
        this.personDetails = personDetails;
    }

    public List<JockeyTrainerResult> getResults() {
        return results;
    }

    public void setResults(List<JockeyTrainerResult> results) {
        this.results = results;
    }
}
