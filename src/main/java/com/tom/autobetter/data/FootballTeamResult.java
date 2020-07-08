package com.tom.autobetter.data;

public class FootballTeamResult {

    private String name;
    private Double score;
    private String winner;

    public FootballTeamResult(){}

    public FootballTeamResult(String name, Double score, String winner){
        this.name = name;
        this.score = score;
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
