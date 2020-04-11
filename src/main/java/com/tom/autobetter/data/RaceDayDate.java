package com.tom.autobetter.data;

import java.util.Calendar;

public class RaceDayDate {

    private static final RaceDayDate raceDayDate = new RaceDayDate();
    private Calendar calendar;

    public static RaceDayDate getInstance(){
        return raceDayDate;
    }

    private RaceDayDate(){
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Integer getYear(){
        return calendar.get(Calendar.YEAR);
    }

    public Integer getMonth(){
        return calendar.get(Calendar.MONTH);
    }

    public Integer getDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
