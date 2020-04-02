package com.tom.autobetter.data;

public class Bet {

    private String race;
    private String horseName;
    private Long id;

    public Bet(String race, String horseName, Long id) {
        this.race = race;
        this.horseName = horseName;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
