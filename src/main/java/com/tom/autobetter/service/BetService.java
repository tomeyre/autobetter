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
import com.tom.autobetter.enums.BetType;
import com.tom.autobetter.repository.dynamodb.AutobetterRepository;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.service.sportinglife.SportingLifeService;
//import com.tom.autobetter.service.sportinglife.betting.WebPageBetting;
import com.tom.autobetter.util.HttpUtil;
import com.tom.autobetter.util.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.tom.autobetter.enums.BetType.*;

@Service
public class BetService {

    public static final String COUNTRY = "United States";//"England";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";

    @Autowired
    SportingLifeService sportingLifeService;

    @Autowired
    SendEmailSMTP sendEmailSMTP;

    @Autowired
    AutobetterRepository autobetterRepository;

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public String placeBets(){

        List<Meet> raceDay = sportingLifeService.getTheDaysRaces().stream().filter(meet -> meet.getMeetingSummary().getCourse().getCountry().getCountry().equalsIgnoreCase(COUNTRY)).collect(Collectors.toList());
        RaceDayEntity betsToPlace = sportingLifeService.workOutBetsToPlace(raceDay);

        autobetterRepository.save(betsToPlace);

        List<RaceDayEntity> raceDayEntities = new ArrayList<>();
        raceDayEntities.add(betsToPlace);

        StringBuilder message = new StringBuilder();
        for(RaceDayEntity raceDayEntity : raceDayEntities) {
            message.append(MessageFormat.format("{0} bets placed across {1} events costing £{2}", totalBets(raceDayEntities), raceDay.size(), calculateCost(raceDayEntities)));
            for (int i = 0; i < raceDayEntity.getEvents().size(); i++) {
                message.append(MessageFormat.format("\r\n{0}, {1} races\r\n\r\n", raceDayEntity.getEvents().get(i).getEventName(), raceDayEntity.getEvents().get(i).getRaces().size()));
                for (Race race : raceDayEntity.getEvents().get(i).getRaces()) {
                    message.append(MessageFormat.format("Race {0} my guess {1} {2}\r\n", race.getRaceTime(), race.getHorseName(), race.getClearWinner() ? "CLEAR WINNER" : ""));
                }
            }
        }

        System.out.println(message.toString());
//        sendEmailSMTP.sendMessage(message.toString(), "Bets have been placed");

        return message.toString();
    }

    public String checkWinners(){
        Optional<RaceDayEntity> rde = autobetterRepository.findById(raceDayDate.getCalendar().getTimeInMillis());
        List<RaceDayEntity>  betsPlaced = new ArrayList<>();
        betsPlaced.add(rde.isPresent() ? rde.get() : null);

        updateWinnersInDb(betsPlaced);

        StringBuilder message = new StringBuilder();
        for(RaceDayEntity raceDayEntity : betsPlaced) {
            message.append(MessageFormat.format("{0} bets placed across {1} events...\r\n", totalBets(betsPlaced), totalEvents(betsPlaced)));
            for (int i = 0; i < raceDayEntity.getEvents().size(); i++) {
                message.append(MessageFormat.format("\r\n{0} {1} out of {2} correct\r\n\r\n", raceDayEntity.getEvents().get(i).getEventName(), raceDayEntity.getWinPercentages().get(i).getWins(), raceDayEntity.getEvents().get(i).getRaces().size()));
                for (Race race : raceDayEntity.getEvents().get(i).getRaces()) {
                    message.append(MessageFormat.format("Race {0} my guess {1}, actual winner {2} {3}\r\n", race.getRaceTime(), race.getHorseName(), race.getWinner(), race.getCorrect()));
                }
            }
        }
        System.out.println(message.toString());
//        sendEmailSMTP.sendMessage(message.toString(), "Results");
        return message.toString();
    }

    private Double calculateCost(List<RaceDayEntity> raceDayEntities){
        Double total = 0.0;
        for(RaceDayEntity raceDayEntity :raceDayEntities){
            for(Event event : raceDayEntity.getEvents()){
                switch (event.getRaces().size()){
                    case 1:
                        total += (ONE.getType() * 0.03);
                        break;
                    case 2:
                        total += (TWO.getType() * 0.03);
                        break;
                    case 3:
                        total += (THREE.getType() * 0.03);
                        break;
                    case 4:
                        total += (FOUR.getType() * 0.03);
                        break;
                    case 5:
                        total += (FIVE.getType() * 0.03);
                        break;
                    case 6:
                        total += (SIX.getType() * 0.03);
                        break;
                    case 7:
                        total += (SEVEN.getType() * 0.03);
                        break;
                    default:
                        total += (EIGHT.getType() * 0.03);
                        break;

                }
            }
        }
        return total;
    }

    private Integer totalBets(List<RaceDayEntity>  betsPlaced){
        Integer response = 0;
        for(Event event : betsPlaced.get(0).getEvents()){
            response += event.getRaces().size();
        }
        return response;
    }

    private Integer totalEvents(List<RaceDayEntity>  betsPlaced){
        return betsPlaced.get(0).getEvents().size();
    }

    private void updateWinnersInDb(List<RaceDayEntity>  betsPlaced){
        List<Meet> raceDay = sportingLifeService.getTheDaysRaces().stream().filter(meet -> meet.getMeetingSummary().getCourse().getCountry().getCountry().equalsIgnoreCase(COUNTRY)).collect(Collectors.toList());
        for(Meet meet : raceDay){
            for(RaceSummary raceSummary : meet.getRaces()){
                HttpUtil httpUtil = new HttpUtil();
                ObjectMapper mapper = new ObjectMapper();
                System.out.println(RACE_URL + raceSummary.getRaceSummaryReference().getId());
                try {
                    raceSummary.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + raceSummary.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {}));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for(RaceDayEntity raceDayEntity : betsPlaced){
            List<WinPercentage> winPercentages = new ArrayList<>();
            for (Event event : raceDayEntity.getEvents()){
                WinPercentage winPercentage = new WinPercentage();
                winPercentage.setRace(event.getEventName());
                winPercentage.setWins(0);
                for(Race race : event.getRaces()) {
                    for (Meet meet : raceDay) {
                        if(meet.getMeetingSummary().getMeetingReference().getId().equals(event.getEventId())) {
                            for (RaceSummary raceSummary : meet.getRaces()) {
                                if(raceSummary.getRaceSummaryReference().getId().equals(race.getRaceId())) {
                                    for (Horse horse : raceSummary.getRaceDetails().getHorses()) {
                                        if(horse.getFinishingPosition() == 1){
                                            race.setWinner(horse.getHorseDetails().getName());
//                                            System.out.println("race winner = " + horse.getHorseDetails().getName() + " :: " + horse.getFinishingPosition());
                                        }
                                        if (horse.getHorseDetails().getName().equalsIgnoreCase(race.getHorseName()) && horse.getFinishingPosition() == 1) {
                                            race.setCorrect(true);
                                            int temp = winPercentage.getWins() == null ? 0 : winPercentage.getWins();
                                            temp++;
                                            winPercentage.setWins(temp);
//                                            System.out.println("Correct winner my guess = " + race.getHorseName() + " :: " + horse.getHorseDetails().getName() + " :: " + horse.getFinishingPosition());
                                        }
                                        if (horse.getHorseDetails().getName().equalsIgnoreCase(race.getHorseName()) && horse.getFinishingPosition() != 1) {
                                            race.setCorrect(false);
//                                            System.out.println("incorrect winner my guess = " + race.getHorseName() + " :: " + horse.getHorseDetails().getName() + " :: " + horse.getFinishingPosition());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                winPercentages.add(winPercentage);
            }
            raceDayEntity.setWinPercentages(winPercentages);
            autobetterRepository.save(raceDayEntity);
        }
    }
}
