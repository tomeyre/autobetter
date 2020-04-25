package com.tom.autobetter.enums;

public enum BetType {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(26),
    SIX(63),
    SEVEN(120),
    EIGHT(247),
    NINE(1),
    TEN(1);


    private Integer type;

    private BetType(Integer type){
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
