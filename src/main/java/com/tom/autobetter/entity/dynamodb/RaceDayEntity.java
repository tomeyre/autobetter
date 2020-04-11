package com.tom.autobetter.entity.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "autobetter")
public class RaceDayEntity {

    private Long eventDate;
//    private Integer raceId;
//    private Integer eventId;
//    private String race;
//    private String horseName;
//    private Boolean correct;
//    private Double odds;

    @DynamoDBHashKey(attributeName = "EVENT_DATE")
    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }


//    @DynamoDBAttribute(attributeName = "EVENT_ID")
//    public Integer getEventId() {
//        return eventId;
//    }
//    @DynamoDBAttribute(attributeName = "RACE_ID")
//    public Integer getRaceId() {
//        return raceId;
//    }
//    @DynamoDBAttribute(attributeName = "RACE")
//    public String getRace() {
//        return race;
//    }
//    @DynamoDBAttribute(attributeName = "HORSE_NAME")
//    public String getHorseName() {
//        return horseName;
//    }
//    @DynamoDBAttribute(attributeName = "CORRECT")
//    public Boolean isCorrect() {
//        return correct;
//    }
//    @DynamoDBAttribute(attributeName = "ODDS")
//    public Double getOdds() {
//        return odds;
//    }

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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