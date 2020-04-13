package com.tom.autobetter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.dynamodb.Race;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.dynamodb.Event;
import com.tom.autobetter.entity.dynamodb.WinPercentage;
import com.tom.autobetter.entity.sporting_life.Horse;
import com.tom.autobetter.entity.sporting_life.Meet;
import com.tom.autobetter.entity.sporting_life.RaceDetails;
import com.tom.autobetter.entity.sporting_life.RaceSummary;
import com.tom.autobetter.repository.dynamodb.AutobetterRepository;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.service.sportinglife.SportingLifeService;
import com.tom.autobetter.util.HttpUtil;
import com.tom.autobetter.util.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
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

    @Autowired
    AutobetterRepository autobetterRepository;


    private RaceDayDate raceDayDate = new RaceDayDate();

    public String placeBets(){
        betfairService.login();

        List<Meet> raceDay = sportingLifeService.getTheDaysRaces().stream().filter(meet -> meet.getMeetingSummary().getCourse().getCountry().getCountry().equalsIgnoreCase("England")).collect(Collectors.toList());
        RaceDayEntity betsToPlace = sportingLifeService.workOutBetsToPlace(raceDay);

        betfairService.placeBetsWithBetfair(betsToPlace);

        List<RaceDayEntity> raceDayEntities = new ArrayList<>();
        raceDayEntities.add(betsToPlace);

        String message = MessageFormat.format("{0} bets placed across {1} events costing £{2}, you have £{3} left to bet with", toFlatList(raceDayEntities, Race.class).size(), raceDay.size(), "0.0", betfairService.getAccountFunds().toString());
        System.out.println(message);
        sendEmailSMTP.sendMessage(message, "Bets have been placed");

        return null;
    }

    public String checkWinners(){
        Optional<RaceDayEntity> rde = autobetterRepository.findById(raceDayDate.getCalendar().getTimeInMillis());
        List<RaceDayEntity>  betsPlaced = new ArrayList<>();
        betsPlaced.add(rde.isPresent() ? rde.get() : null);

        updateWinnersInDb(betsPlaced);

        StringBuilder message = new StringBuilder();
        message.append(MessageFormat.format("{0} bets placed across {1} events...\r\n", toFlatList(betsPlaced, Race.class).size(), toFlatList(betsPlaced, Event.class).size()));
        for(RaceDayEntity raceDayEntity : betsPlaced) {
            for (int i = 0; i < raceDayEntity.getEvents().size(); i++) {
                message.append(MessageFormat.format("{0} {1} out of {2} correct\r\n", raceDayEntity.getEvents().get(i).getEventName(), raceDayEntity.getWinPercentages().get(i).getWins(), raceDayEntity.getEvents().get(i).getRaces().size()));
                for (Race race : raceDayEntity.getEvents().get(i).getRaces()) {
                    message.append(MessageFormat.format("Race {0} my guess {1}, actual winner {2}\r\n", race.getRaceId(), race.getHorseName(), race.getCorrect()));
                }
            }
        }
        System.out.println(message.toString());
        sendEmailSMTP.sendMessage(message.toString(), "Results");
        return null;
    }

    private void updateWinnersInDb(List<RaceDayEntity>  betsPlaced){
        List<Meet> raceDay = sportingLifeService.getTheDaysRaces().stream().filter(meet -> meet.getMeetingSummary().getCourse().getCountry().getCountry().equalsIgnoreCase("England")).collect(Collectors.toList());
        for(RaceDayEntity raceDayEntity : betsPlaced){
            for (Event event : raceDayEntity.getEvents()){
                WinPercentage winPercentage = new WinPercentage();
                winPercentage.setRace(event.getEventName());
                for(Race race : event.getRaces()){
                    for(Meet meet : raceDay){
                        for(RaceSummary raceSummary : meet.getRaces()){
                            String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";
                            HttpUtil httpUtil = new HttpUtil();
                            ObjectMapper mapper = new ObjectMapper();
                            System.out.println(RACE_URL + raceSummary.getRaceSummaryReference().getId());
                            try {
                                raceSummary.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + raceSummary.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {}));
                                for(Horse horse : raceSummary.getRaceDetails().getHorses()){
                                    if(horse.getHorseDetails().getName().equalsIgnoreCase(race.getHorseName()) && horse.getFinishingPosition() == 1){
                                        race.setCorrect(true);
                                        int temp = winPercentage.getWins() == null ? 0 : winPercentage.getWins();
                                        temp++;
                                        winPercentage.setWins(temp);
                                        break;
                                    }
                                    if(horse.getHorseDetails().getName().equalsIgnoreCase(race.getHorseName()) && horse.getFinishingPosition() != 1){
                                        race.setCorrect(false);
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if(raceDayEntity.getWinPercentages() == null || raceDayEntity.getWinPercentages().isEmpty()){
                    List<WinPercentage> winPercentages = new ArrayList<>();
                    winPercentages.add(winPercentage);
                    raceDayEntity.setWinPercentages(winPercentages);
                }else{
                    List<WinPercentage> winPercentages = raceDayEntity.getWinPercentages();
                    winPercentages.add(winPercentage);
                    raceDayEntity.setWinPercentages(winPercentages);
                }
            }
            autobetterRepository.save(raceDayEntity);
        }
    }

    private static <T> List<T> toFlatList(Collection<?> collection, Class<T> targetType) {
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
