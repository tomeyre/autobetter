package com.tom.autobetter.util;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.sporting_life.Horse;
import com.tom.autobetter.entity.sporting_life.JockeyTrainerResult;

import java.util.List;
import java.util.stream.Collectors;

public class CalculationUtil {

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public Integer hasTheHorseWonAnyOfTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 1))
                .collect(Collectors.toList())
                .size() * 2;
    }

    public Integer hasTheHorseFinishedSecondInTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 2))
                .collect(Collectors.toList())
                .size();

    }

    public Double doesTheJockeyOftenWin(Horse horse){
        if (horse.getJockey().getResults() == null) {
            return 0.0;
        }
        List<JockeyTrainerResult> results = horse.getJockey().getResults()
                .stream()
                .filter(jockeyTrainerResult -> !raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) && jockeyTrainerResult.getResult().getPosition() != null)
                .collect(Collectors.toList());
        try {
            return ((100.0 / results.size()) * results
                    .stream()
                    .filter(jockeyTrainerResult -> jockeyTrainerResult.getResult().getPosition() == 1)
                    .collect(Collectors.toList())
                    .size()) / 10.0;
        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    public Double doesTheTrainerTrainWinningHorses(Horse horse){
        if (horse.getTrainer().getResults() == null) {
            return 0.0;
        }
        List<JockeyTrainerResult> results = horse.getTrainer().getResults()
                .stream()
                .filter(jockeyTrainerResult -> !raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) && jockeyTrainerResult.getResult().getPosition() != null)
                .collect(Collectors.toList());
        try {
            return ((100.0 / results.size()) * results
                    .stream()
                    .filter(jockeyTrainerResult -> jockeyTrainerResult.getResult().getPosition() == 1)
                    .collect(Collectors.toList())
                    .size()) / 10.0;
        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    public Integer hasThisJockeyWonWithThisHorseBefore(Horse horse) {
        if (horse.getJockey().getResults() == null) {
            return 0;
        }
        return horse.getJockey().getResults()
                .stream()
                .filter(jockeyTrainerResult ->
                        !raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) &&
                                jockeyTrainerResult.getHorseReference().getHorseReference().getId() == horse.getHorseDetails().getHorseReference().getId() &&
                                jockeyTrainerResult.getResult().getPosition() != null &&
                                jockeyTrainerResult.getResult().getPosition() == 1)
                .collect(Collectors.toList())
                .size() * 3;

    }

    public Integer hasTheHorseRacedInThisClassBeforeAndWon(Horse horse, Integer currentRaceClass){
        return horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) &&
                        (result.getRaceClass() != null && result.getRaceClass() == currentRaceClass) &&
                        (result.getPosition() != null && result.getPosition() == 1))
                .collect(Collectors.toList())
                .size();
    }


    //
    //hasTheHorseRacedWithThisWeightBeforeAndWon
    //isTheHorseComfortableAtThisDistance
    //isTheHorseReliable
    //isTheJockeyReliable
    //doesTheHorseFavourThisGround
    //hasTheLast6RacesBeenRecent
    //hasTheHorseMovedClass

    public Double horseOdds(Horse horse){
        if(Character.isDigit(horse.getBetting().getCurrentOdds().charAt(0))) {
            int a = Integer.parseInt(horse.getBetting().getCurrentOdds().split("/")[0]);
            int b = Integer.parseInt(horse.getBetting().getCurrentOdds().split("/")[1]);
            return (b / a) * 10.0;
        }
        return 0.0;
    }

    public Integer horseRatingBonus(Horse horse){
        if(horse.getOfficialRating() != null) {
            return horse.getOfficialRating();
        }
        return 0;
    }
}
