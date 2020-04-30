package com.tom.autobetter.data;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Component;

import java.util.Calendar;

public final class RaceDayDate {

    private static RaceDayDate raceDayDate;
    private Calendar calendar;

    public static RaceDayDate getInstance(){
        if(raceDayDate == null){
            raceDayDate = new RaceDayDate();
        }
        return raceDayDate;
    }

    public void setCalendar(String dateString){
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateString.split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateString.split("-")[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateString.split("-")[2]));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public Boolean todayOrFutureDate(Calendar resultDate){
        if(resultDate.get(Calendar.YEAR) > calendar.get(Calendar.YEAR)){
            return true;
        }
        if(resultDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                resultDate.get(Calendar.MONTH) > calendar.get(Calendar.MONTH)){
            return true;
        }
        if(resultDate.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                resultDate.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                resultDate.get(Calendar.DAY_OF_MONTH) >= calendar.get(Calendar.DAY_OF_MONTH)){
            return true;
        }
        return false;
    }

    public Boolean isDateWithingXMonthsOfRaceDate(Calendar resultDate, Integer xMonths){
        Calendar sixMonthsAgo = Calendar.getInstance();
        sixMonthsAgo.setTimeInMillis(calendar.getTimeInMillis());
        sixMonthsAgo.set(Calendar.MONTH, -xMonths);
        Long diff = resultDate.getTimeInMillis() - sixMonthsAgo.getTimeInMillis();
        if(diff / 1000*60*60*24 > 1000*60*60*24 + (31*3) && diff > 0){
            return true;
        }
        return false;
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
