package com.tom.autobetter.data;

public class Winner {

    private String name;
    private Double odds;
    private Double score;
    private Integer finishPosition;
    private Boolean correct;
    private Boolean clearWinner;

    public Boolean getClearWinner() {
        return clearWinner;
    }

    public void setClearWinner(Boolean clearWinner) {
        this.clearWinner = clearWinner;
    }

    public Integer getFinishPosition() {
        return finishPosition;
    }

    public void setFinishPosition(Integer finishPosition) {
        this.finishPosition = finishPosition;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
