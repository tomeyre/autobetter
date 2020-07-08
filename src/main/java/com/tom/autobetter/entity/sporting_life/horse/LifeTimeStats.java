package com.tom.autobetter.entity.sporting_life.horse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LifeTimeStats {
    @JsonProperty(value = "ride_id")
    private Long id;
    @JsonProperty(value = "run_count")
    private Integer runCount;
    @JsonProperty(value = "win_count")
    private Integer wins;
    @JsonProperty(value = "place_count")
    private Integer placed;
}

