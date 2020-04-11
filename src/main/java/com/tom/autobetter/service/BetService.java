package com.tom.autobetter.service;

import com.tom.autobetter.data.Bet;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.dynamodb.Race;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.dynamodb.Event;
import com.tom.autobetter.entity.sporting_life.Meet;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.service.sportinglife.SportingLifeService;
import com.tom.autobetter.util.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BetService {

    @Autowired
    BetfairService betfairService;

    @Autowired
    SportingLifeService sportingLifeService;

    @Autowired
    SendEmailSMTP sendEmailSMTP;


    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public String placeBets(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2020);
        calendar.set(Calendar.MONTH,3);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        raceDayDate.setCalendar(calendar);

        betfairService.login();
        List<Meet> raceDay = sportingLifeService.getTheDaysRaces().stream().filter(meet -> meet.getMeetingSummary().getCourse().getCountry().getCountry().equalsIgnoreCase("England")).collect(Collectors.toList());
        List<Bet> betsToPlace = sportingLifeService.workOutBetsToPlace(raceDay);
        for(Bet bet : betsToPlace){
            System.out.println(bet.getRace() + " " + bet.getHorseName() + " " + bet.getBetId());
        }
        betfairService.placeBetsWithBetfair(betsToPlace);
        String message = MessageFormat.format("{0} bets placed across {1} events costing £{2}, you have £{3} left to bet with", Integer.toString(betsToPlace.size()), raceDay.size(), "0.0", betfairService.getAccountFunds().toString());
        sendEmailSMTP.sendMessage(message);

        return null;
    }

    public String checkWinners(){
        List<RaceDayEntity>  betsPlaced = sportingLifeService.reportWinners();
        StringBuilder message = new StringBuilder();
        message.append(MessageFormat.format("{0} bets placed across {1} events...\n", toFlatList(betsPlaced, Race.class).size(), toFlatList(betsPlaced, Event.class).size()));
        for(RaceDayEntity raceDayEntity : betsPlaced) {
            for (Event event : raceDayEntity.getEvents()) {
                for (Race race : event.getRaces()) {
                    message.append(MessageFormat.format("Race {0} my guess {1}, actual winner {2}\n", race.getRaceId(), race.getHorseName(), race.getCorrect()));
                }
            }
        }
        sendEmailSMTP.sendMessage(message.toString());
        return null;
    }

    public static <T> List<T> toFlatList(Collection<?> collection, Class<T> targetType) {
        List<Object> result =
                collection.stream()
                        .flatMap(child -> {
                            if (child == null) {
                                return Stream.empty();
                            } else if (child instanceof Collection) {
                                return toFlatList((Collection<?>) child, targetType)
                                        .stream();
                            } else if (child.getClass().isAssignableFrom(targetType)) {
                                return Stream.of(child);
                            } else {
                                //ignore because it's not targetType T
                                return Stream.empty();
                            }
                        })
                        .collect(Collectors.toList());
        return (List<T>) result;
    }
}
