package com.tom.autobetter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.FootballTeamResult;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.dynamodb.Race;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.dynamodb.Event;
import com.tom.autobetter.entity.dynamodb.WinPercentage;
import com.tom.autobetter.entity.sporting_life.horse.Horse;
import com.tom.autobetter.entity.sporting_life.horse.Meet;
import com.tom.autobetter.entity.sporting_life.horse.RaceDetails;
import com.tom.autobetter.entity.sporting_life.horse.RaceSummary;
import com.tom.autobetter.entity.sporting_life.football.FootballMatch;
import com.tom.autobetter.repository.dynamodb.AutobetterRepository;
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

import static com.tom.autobetter.enums.BetType.*;

@Service
public class BetService {

    public static final String COUNTRY = "United States";//"England";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";

    final int THRESHHOLD = 5;

    @Autowired
    SportingLifeService sportingLifeService;

    @Autowired
    SendEmailSMTP sendEmailSMTP;

    @Autowired
    AutobetterRepository autobetterRepository;

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public String placeHorseBets(){

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
                    message.append(MessageFormat.format("Race {0} my GUESS {1} {2}\r\n", race.getRaceTime(), race.getHorseName(), race.getClearWinner() ? "CLEAR WINNER" : ""));
                }
            }
        }

        System.out.println(message.toString());
//        sendEmailSMTP.sendMessage(message.toString(), "Bets have been placed");

        return message.toString();
    }

    public String placeFootballBets(){
        List<FootballMatch> daysMatches = sportingLifeService.getFootballMatchesForDate();
        return workOutBetsToPlace(daysMatches);
        //iterate through list each time getting the form
        //https://www.sportinglife.com/api/football/form?match_id={can be gotten from the above}
        //https://www.sportinglife.com/api/football/team/5 historic results for team

//        System.out.println(daysMatches.size());

//        return null;
    }

    private String workOutBetsToPlace(List<FootballMatch> daysMatches){
        HashMap<String, FootballTeamResult> results = new HashMap<>();
        int correct = 0;
        int matches = 0;
        StringBuilder sb = new StringBuilder();
        for(FootballMatch match : daysMatches){
            if(match.getCompetition().getCompetitionReference().getId() == 1) {
                matches++;
                results.put(match.getTeamScoreA().getTeam().getName(), sportingLifeService.getFootballMatchDetailsById(match.getTeamScoreA().getTeam().getTeamReference().getId(), match.getTeamScoreB().getTeam().getName()));
                results.put(match.getTeamScoreB().getTeam().getName(), sportingLifeService.getFootballMatchDetailsById(match.getTeamScoreB().getTeam().getTeamReference().getId(), match.getTeamScoreA().getTeam().getName()));

                System.out.println(sb.append(match.getTeamScoreA().getTeam().getName() + " " + results.get(match.getTeamScoreA().getTeam().getName()).getScore() + " \nvs \n" + match.getTeamScoreB().getTeam().getName() + " " + results.get(match.getTeamScoreB().getTeam().getName()).getScore() + " \nwinner is " +
                        ((results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                results.get(match.getTeamScoreA().getTeam().getName()).getScore() - results.get(match.getTeamScoreB().getTeam().getName()).getScore() :
                                results.get(match.getTeamScoreB().getTeam().getName()).getScore() - results.get(match.getTeamScoreA().getTeam().getName()).getScore()) < THRESHHOLD ?
                                "DRAW" : results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                match.getTeamScoreA().getTeam().getName() :
                                 match.getTeamScoreB().getTeam().getName()) +
                        " \nactual winner" +
                        (match.getState().equalsIgnoreCase("FULLTIME") ?
                                results.get(match.getTeamScoreA().getTeam().getName()).getWinner().equalsIgnoreCase(
                                        ((results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                                results.get(match.getTeamScoreA().getTeam().getName()).getScore() - results.get(match.getTeamScoreB().getTeam().getName()).getScore() :
                                                results.get(match.getTeamScoreB().getTeam().getName()).getScore() - results.get(match.getTeamScoreA().getTeam().getName()).getScore()) < THRESHHOLD ?
                                                "DRAW" : results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                                match.getTeamScoreA().getTeam().getName() :
                                                match.getTeamScoreB().getTeam().getName())) ? " CORRECT" : " INCORRECT" : " UNKNOWN") +
                        " \nPOINT DIF " +
                        (results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                results.get(match.getTeamScoreA().getTeam().getName()).getScore() - results.get(match.getTeamScoreB().getTeam().getName()).getScore() :
                                results.get(match.getTeamScoreB().getTeam().getName()).getScore() - results.get(match.getTeamScoreA().getTeam().getName()).getScore())));
                correct += results.get(match.getTeamScoreA().getTeam().getName()).getWinner().equalsIgnoreCase(
                        ((results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                results.get(match.getTeamScoreA().getTeam().getName()).getScore() - results.get(match.getTeamScoreB().getTeam().getName()).getScore() :
                                results.get(match.getTeamScoreB().getTeam().getName()).getScore() - results.get(match.getTeamScoreA().getTeam().getName()).getScore()) < THRESHHOLD ?
                                "DRAW" : results.get(match.getTeamScoreA().getTeam().getName()).getScore() > results.get(match.getTeamScoreB().getTeam().getName()).getScore() ?
                                match.getTeamScoreA().getTeam().getName() :
                                match.getTeamScoreB().getTeam().getName())) ? 1 : 0;
                sb.append("\n\n");
            }
        }
        System.out.println(sb.append("Correct : " + correct + " out of " + matches));

        return sb.toString();
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
                    message.append(MessageFormat.format("Race {0} my GUESS {1}, WINNER {2} :: {3} ODDS {4} DIFF {5}\r\n", race.getRaceTime(), race.getHorseName(), race.getWinner(), race.getCorrect(), race.getOdds(), race.getScore()));
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
