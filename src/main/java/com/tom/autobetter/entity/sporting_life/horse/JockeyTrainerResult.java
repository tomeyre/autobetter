package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties
public class JockeyTrainerResult {

    @JsonProperty(value = "horse_summary")
    private HorseReference horseReference;
    @JsonProperty(value = "horse_entry")
    private Result result;

    public HorseReference getHorseReference() {
        return horseReference;
    }

    public void setHorseReference(HorseReference horseReference) {
        this.horseReference = horseReference;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
