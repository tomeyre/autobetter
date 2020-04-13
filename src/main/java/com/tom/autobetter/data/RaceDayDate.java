package com.tom.autobetter.data;

import java.util.Calendar;
import java.util.Date;

public final class RaceDayDate {

    private static RaceDayDate raceDayDate;
    private static Calendar calendar;

    public static RaceDayDate getInstance(){
        if(raceDayDate == null){
            raceDayDate = new RaceDayDate();
            calendar = Calendar.getInstance();
            String dateString = System.getenv("date");
            if(dateString != null) {
                calendar.set(Calendar.YEAR, Integer.parseInt(dateString.split("-")[0]));
                calendar.set(Calendar.MONTH, Integer.parseInt(dateString.split("-")[1]));
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString.split("-")[2]));
            }else {
                Date date = new Date();
                calendar.setTime(date);
            }
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
