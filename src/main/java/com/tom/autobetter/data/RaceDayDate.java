package com.tom.autobetter.data;

import java.util.Calendar;

public final class RaceDayDate {

    private static RaceDayDate raceDayDate;
    private static Calendar calendar;

    public static RaceDayDate getInstance(){
        if(raceDayDate == null){
            raceDayDate = new RaceDayDate();
            calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,2020);
            calendar.set(Calendar.MONTH,2);
            calendar.set(Calendar.DAY_OF_MONTH,1);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        }
        return raceDayDate;
    }

    private RaceDayDate(){
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() { return calendar;}

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
