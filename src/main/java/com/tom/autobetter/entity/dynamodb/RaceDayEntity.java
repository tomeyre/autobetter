package com.tom.autobetter.entity.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "autobetter")
public class RaceDayEntity {

    private Long eventDate;
    private List<Event> events;
    private List<WinPercentage> winPercentages;

    @DynamoDBHashKey(attributeName = "EVENT_DATE")
    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<WinPercentage> getWinPercentages() {
        return winPercentages;
    }

    public void setWinPercentages(List<WinPercentage> winPercentages) {
        this.winPercentages = winPercentages;
    }
}


//        public void setEventId(Integer eventId) {
//        this.eventId = eventId;
//    }
//    public void setRaceId(Integer raceId) {
//        this.raceId = raceId;
//    }
//    public void setRace(String race) {
//        this.race = race;
//    }
//    public void setHorseName(String horseName) {
//        this.horseName = horseName;
//    }
//    public void setCorrect(Boolean correct) {
//        this.correct = correct;
//    }
//    public void setOdds(Double odds) {
//        this.odds = odds;
//    }
//
//}