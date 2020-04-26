package com.tom.autobetter.service.sportinglife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.data.Winner;
import com.tom.autobetter.entity.betfair.MarketFilter;
import com.tom.autobetter.entity.dynamodb.Event;
import com.tom.autobetter.entity.dynamodb.Race;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.sporting_life.*;
import com.tom.autobetter.repository.dynamodb.AutobetterRepository;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.util.CalculationUtil;
import com.tom.autobetter.util.HttpUtil;
import jdk.nashorn.internal.scripts.JO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SportingLifeService {

//    @Autowired
//    private BetfairService betfairService;

    @Autowired
    AutobetterRepository autobetterRepository;

    private CalculationUtil calculationUtil = new CalculationUtil();
    private RaceDayDate raceDayDate = RaceDayDate.getInstance();
    private static final String RACE_DAY_URL = "https://www.sportinglife.com/api/horse-racing/racing/racecards/";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";
    private static final String HORSE_URL = "https://www.sportinglife.com/api/horse-racing/horse/";
    private static final String JOCKEY_URL = "https://www.sportinglife.com/api/horse-racing/jockey/";
    private static final String TRAINER_URL = "https://www.sportinglife.com/api/horse-racing/trainer/";
    private HttpUtil httpUtil = new HttpUtil();
    private ObjectMapper mapper = new ObjectMapper();

    public List<Meet> getTheDaysRaces(){

        System.out.println(RACE_DAY_URL + raceDayDate.getYear()+ "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        String json = httpUtil.getJSONFromUrl(RACE_DAY_URL + raceDayDate.getYear()+ "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        try {
            return mapper.readValue(json, new TypeReference<List<Meet>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RaceDayEntity workOutBetsToPlace(List<Meet> raceDay){
        RaceDayEntity response = new RaceDayEntity();
        response.setEventDate(raceDayDate.getCalendar().getTimeInMillis());

        MarketFilter marketFilter;
        marketFilter = new MarketFilter();
        Set<String> eventTypeIds = new HashSet<String>();
        eventTypeIds.add("7");
        marketFilter.setEventIds(eventTypeIds);
        List<Event> newEvents = new ArrayList<>();
        response.setEvents(newEvents);

//        List<MarketCatalogue> marketCatalogueList = betfairService.getHorseRacesToday(calendar);
        raceDay.stream().forEach(meet ->
        {
            List<Event> events = response.getEvents();
            events.add(createNewEvent(meet));
            response.setEvents(events);
            meet.getRaces().stream().forEach(race -> {
                try {
                    System.out.println(RACE_URL + race.getRaceSummaryReference().getId());
                    race.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + race.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {}));

                    for(Horse horse : race.getRaceDetails().getHorses()){
                        if(horse.getHorseDetails().getPreviousResults() != null && horse.getHorseDetails().getPreviousResults().size() == 6) {
                            System.out.println(HORSE_URL + horse.getHorseDetails().getHorseReference().getId());
                            horse.getHorseDetails().setPreviousResults(mapper.readValue(new JSONObject(httpUtil.getJSONFromUrl(HORSE_URL + horse.getHorseDetails().getHorseReference().getId())).getJSONArray("previous_results").toString(), new TypeReference<List<Result>>() {}));
                        }
                        System.out.println(JOCKEY_URL + horse.getJockey().getJockeyReference().getId());
                        if(horse.getJockey() != null && horse.getJockey().getJockeyReference() != null) {
                            horse.setJockey(mapper.readValue(httpUtil.getJSONFromUrl(JOCKEY_URL + horse.getJockey().getJockeyReference().getId()), new TypeReference<Jockey>() {}));
                        }
//                        System.out.println(TRAINER_URL + horse.getTrainer().getTrainerReference().getId());
//                        horse.setTrainer(mapper.readValue(httpUtil.getJSONFromUrl(TRAINER_URL + horse.getTrainer().getTrainerReference().getId()), new TypeReference<Trainer>() {}));
                    }

                    Winner winner = workOutRaceWinner(race);
                    System.out.println(winner.getName() + " :: " + winner.getCorrect());

                    List<Race> raceList = response.getEvents().stream().filter(event -> event.getEventId() == meet.getMeetingSummary().getMeetingReference().getId()).findFirst().get().getRaces();
                    raceList.add(createNewRace(race, winner/*, runner.getSelectionId()*/));
                    response.getEvents().stream().filter(event -> event.getEventId() == meet.getMeetingSummary().getMeetingReference().getId()).findFirst().get().setRaces(raceList);

                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
        return response;
    }

    private Race createNewRace(RaceSummary race, Winner winner/*, Long betId*/){
        Race raceEntity = new Race();
        raceEntity.setCorrect(null);
        raceEntity.setHorseName(winner.getName());
        raceEntity.setOdds(winner.getOdds());
        raceEntity.setRace(race.getCourseName());
        raceEntity.setRaceId(race.getRaceSummaryReference().getId());
        raceEntity.setBetId(1l);
        raceEntity.setRaceTime(race.getTime());
        return raceEntity;
    }

    private Event createNewEvent(Meet meet){
        Event event = new Event();
        event.setEventId(meet.getMeetingSummary().getMeetingReference().getId());
        event.setEventName(meet.getMeetingSummary().getCourse().getName());
        List<Race> races = new ArrayList<>();
        event.setRaces(races);
        return event;
    }

    private Winner workOutRaceWinner(RaceSummary race){
        List<Winner> rankings = new ArrayList<>();
        for(Horse horse : race.getRaceDetails().getHorses()){
            Winner temp = new Winner();
            temp.setName(horse.getHorseDetails().getName());
            temp.setOdds(workoutOddsPercentage(horse.getBetting().getCurrentOdds()));
            if(horse.getRunning()) {
                temp.setScore(calculateChanceOfWinning(horse, race.getRaceClass()));
            }else{
                temp.setScore(-1d);
            }
            if(horse.getFinishingPosition() != null){
                temp.setFinishPosition(horse.getFinishingPosition());
            }
            rankings.add(temp);
        }
        rankings.sort(horseComparitor);
        int count = 1;
        Double score = 0d;
        for (Winner winner : rankings){
            if(score == 0){
                score = winner.getScore();
            }
            else if(score == winner.getScore()){
                count++;
            }
        }
        System.out.println("potential winners for race : " + count);
        System.out.println(rankings.get(0).getFinishPosition());
        rankings.get(0).setCorrect(rankings.get(0).getFinishPosition() == 1);
        return rankings.get(0);
    }

    Comparator<Winner> horseComparitor = new Comparator<Winner>() {
        @Override
        public int compare(Winner e1, Winner e2) {
            return e2.getScore().compareTo(e1.getScore());
        }
    };

    private Double workoutOddsPercentage(String odds){
        if(odds != null && !odds.equalsIgnoreCase("") && Character.isDigit(odds.charAt(0))){
            Double a = Double.parseDouble(odds.split("/")[0]);
            Double b = Double.parseDouble(odds.split("/")[1]);
            return 1/(a/b);
        }
        return 0.0;
    }

    private Double calculateChanceOfWinning(Horse horse, Integer raceClass){
        Double score = 0.0;
        System.out.println("-----------------------------------");
        score += calculationUtil.hasTheHorseWonAnyOfTheirLastRaces(horse).doubleValue();
        score += calculationUtil.hasTheHorseFinishedSecondInTheirLastRaces(horse).doubleValue();
        score += calculationUtil.hasThisJockeyWonWithThisHorseBefore(horse).doubleValue();
        score += calculationUtil.didTheHorseWinItsLastRace(horse);
//        score += calculationUtil.hasTheHorseRacedInThisClassBeforeAndWon(horse, raceClass).doubleValue();
//        score += calculationUtil.hasTheHorseRacedWithThisWeightBeforeAndWon(horse);
//        score += calculationUtil.isTheHorseReliable(horse);
//        score += calculationUtil.isTheJockeyReliable(horse);
//        score += calculationUtil.isTheHorseComfortableAtThisDistance(horse);
//        score += calculationUtil.doesTheHorseFavourThisGround(horse);
//        score += calculationUtil.hasTheLast6RacesBeenRecent(horse);
//        score += calculationUtil.hasTheHorseMovedClass(horse);
//        score += calculationUtil.horseRatingBonus(horse);
//        score += calculationUtil.horseOdds(horse);
        if(score == 0){
        score += calculationUtil.doesTheJockeyOftenWin(horse);
        score += calculationUtil.horseOdds(horse);
//        score += calculationUtil.doesTheTrainerTrainWinningHorses(horse);
        }
        System.out.println(horse.getHorseDetails().getName() + " Overall score " + score + "-----------------------------------");
        return score;
    }
}
