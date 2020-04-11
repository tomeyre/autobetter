package com.tom.autobetter.service.sportinglife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.Bet;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.data.Winner;
import com.tom.autobetter.entity.betfair.MarketFilter;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.sporting_life.Horse;
import com.tom.autobetter.entity.sporting_life.Meet;
import com.tom.autobetter.entity.sporting_life.RaceDetails;
import com.tom.autobetter.entity.sporting_life.RaceSummary;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.util.CalculationUtil;
import com.tom.autobetter.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SportingLifeService {

    @Autowired
    private BetfairService betfairService;

    private CalculationUtil calculationUtil = new CalculationUtil();
    private RaceDayDate raceDayDate = RaceDayDate.getInstance();
    private static final String RACE_DAY_URL = "https://www.sportinglife.com/api/horse-racing/racing/racecards/";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";
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

    public List<Bet> workOutBetsToPlace(List<Meet> raceDay){
        List<Bet> response = new ArrayList();
        MarketFilter marketFilter;
        marketFilter = new MarketFilter();
        Set<String> eventTypeIds = new HashSet<String>();
        eventTypeIds.add("7");
        marketFilter.setEventIds(eventTypeIds);

//        List<MarketCatalogue> marketCatalogueList = betfairService.getHorseRacesToday(calendar);
        raceDay.stream().forEach(meet -> meet.getRaces().stream().forEach(race -> {
            try {
                System.out.println(RACE_URL + race.getRaceSummaryReference().getId());
                race.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + race.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {}));
                Winner winner = workOutRaceWinner(race);
                System.out.println(winner.getName() + " :: " + winner.getCorrect());

//                for(MarketCatalogue marketCatalogue : marketCatalogueList){
//                    for(RunnerCatalog runner : marketCatalogue.getRunners()){
//                        if(runner.getRunnerName().toLowerCase().contains(race.getRaceDetails().getHorses().get(0).getHorseDetails().getName().toLowerCase())){
//                            response.add(new Bet(race.getCourseName(),winner.getName(),1l, race.getTime(),race.getMeetingSummaryReference().getId(), winner.getOdds()));//runner.getSelectionId()));
                //String race, String horseName, Long betId, String time, Integer raceId, Double odds
//                            break;
//                        }
//                    }
//                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }));
        return response;
    }

    public List<RaceDayEntity> reportWinners(){
        return null;
    }

    private Winner workOutRaceWinner(RaceSummary race){
        List<Winner> rankings = new ArrayList<>();
        for(Horse horse : race.getRaceDetails().getHorses()){
            Winner temp = new Winner();
            temp.setName(horse.getHorseDetails().getName());
            temp.setOdds(workoutOddsPercentage(horse.getBetting().getCurrentOdds()));
            temp.setScore(calculateChanceOfWinning(horse));
            if(horse.getFinishingPosition() != null){
                temp.setFinishPosition(horse.getFinishingPosition());
            }
            rankings.add(temp);
        }
        rankings.sort(horseComparitor);
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

    private Double calculateChanceOfWinning(Horse horse){
        Double score = 0.0;
//        System.out.println("-----------------------------------");
        score += calculationUtil.hasTheHorseWonAnyOfTheirLastRaces(horse).doubleValue();
        score += calculationUtil.hasTheHorseFinishedSecondInTheirLastRaces(horse).doubleValue();
//        score += calculationUtil.doesTheJockeyOftenWin(horse);
//        score += calculationUtil.doesTheTrainerTrainWinningHorses(horse);
//        score += calculationUtil.hasThisJokeyWonWithThisHorseBefore(horse);
//        score += calculationUtil.hasTheHorseRacedInThisClassBeforeAndWon(horse);
//        score += calculationUtil.hasTheHorseRacedWithThisWeightBeforeAndWon(horse);
//        score += calculationUtil.isTheHorseReliable(horse);
//        score += calculationUtil.isTheJockeyReliable(horse);
//        score += calculationUtil.isTheHorseComfortableAtThisDistance(horse);
//        score += calculationUtil.doesTheHorseFavourThisGround(horse);
//        score += calculationUtil.hasTheLast6RacesBeenRecent(horse);
//        score += calculationUtil.hasTheHorseMovedClass(horse);
//        score += calculationUtil.horseRatingBonus(horse);
//        score += calculationUtil.horseOdds(horse);
//        System.out.println(horse.getHorseDetails().getName() + " Overall score " + score + "-----------------------------------");
        return score;
    }
}
