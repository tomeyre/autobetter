package com.tom.autobetter.entity.sporting_life;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meet {

    @JsonProperty(value = "meeting_summary")
    private MeetingSummary meetingSummary;
    @JsonProperty(value = "races")
    private List<RaceSummary> races;

    public MeetingSummary races() {
        return meetingSummary;
    }

    public void setMeetingSummary(MeetingSummary meetingSummary) {
        this.meetingSummary = meetingSummary;
    }

    public List<RaceSummary> getRaces() {
        return races;
    }

    public void setRaces(List<RaceSummary> races) {
        this.races = races;
    }
}
