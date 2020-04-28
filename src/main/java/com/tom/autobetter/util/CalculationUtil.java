package com.tom.autobetter.util;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.data.Winner;
import com.tom.autobetter.entity.betfair.Data;
import com.tom.autobetter.entity.sporting_life.Horse;
import com.tom.autobetter.entity.sporting_life.JockeyTrainerResult;
import com.tom.autobetter.entity.sporting_life.Result;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CalculationUtil {

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public Integer hasTheHorseWonAnyOfTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 1))
                .collect(Collectors.toList())
                .size();
    }

    public Double didTheHorseWinItsLastRace(Horse horse) {
        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            if (results.get(0).getPosition() == 1) {
                return 1d;
            }
        }
        return 0d;
    }

    public Integer hasTheHorseFinishedSecondInTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 2))
                .collect(Collectors.toList())
                .size();

    }

    public Double checkjockeyRecentPerformance(Horse horse){
        if (horse.getJockey().getResults() == null) {
            return 0.0;
        }
        List<JockeyTrainerResult> results = horse.getJockey().getResults()
                .stream()
                .filter(jockeyTrainerResult -> !raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) && jockeyTrainerResult.getResult().getPosition() != null)
                .collect(Collectors.toList());
        try {
            return (1.0 / results.size()) * results
                    .stream()
                    .filter(jockeyTrainerResult -> jockeyTrainerResult.getResult().getPosition() == 1)
                    .collect(Collectors.toList())
                    .size();
        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    public Double checkJockeyRecentPerformance(Horse horse, Integer howManyResultsToCheck){
        int count = 0;
        Double response = 0d;

        if (horse.getJockey().getResults() == null) {
            return 0.0;
        }
        List<JockeyTrainerResult> results = horse.getJockey().getResults()
                .stream()
                .filter(jockeyTrainerResult -> !raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) && jockeyTrainerResult.getResult().getPosition() != null &&jockeyTrainerResult.getResult().getPosition() > 0)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(jockeyTrainerResultComparator);
            for(JockeyTrainerResult result : results) {
//                System.out.println(result.getResult().getDate().get(Calendar.YEAR) + "/"+ result.getResult().getDate().get(Calendar.MONTH) + "/" +result.getResult().getDate().get(Calendar.DAY_OF_MONTH));
                response += ((100 / result.getResult().getRunnerCount()) * result.getResult().getPosition()) + ((100 / result.getResult().getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response == 0){
                return 0d;
            }
            return (100d - (response / count)) / 100;
        }
        return 0d;
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
            return (100.0 / results.size()) * results
                    .stream()
                    .filter(jockeyTrainerResult -> jockeyTrainerResult.getResult().getPosition() == 1)
                    .collect(Collectors.toList())
                    .size();
        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }

    public Double hasThisJockeyWonWithThisHorseBeforeRecently(Horse horse, Integer howManyResultsToCheck) {
        Double response = 0d;
        int count = 0;
        if (horse.getJockey().getResults() == null) {
            return response;
        }
        for (JockeyTrainerResult jockeyTrainerResult : horse.getJockey().getResults()) {
            if (!raceDayDate.todayOrFutureDate(jockeyTrainerResult.getResult().getDate()) &&
                    jockeyTrainerResult.getHorseReference().getHorseReference().getId() == horse.getHorseDetails().getHorseReference().getId() &&
                    jockeyTrainerResult.getResult().getPosition() != null &&
                    jockeyTrainerResult.getResult().getPosition() == 1) {
                response++;
                count++;
            }
            if (count == howManyResultsToCheck) {
                break;
            }
            if(response == 0d){
                return response;
            }
            return 100d / ((100d / count) * response);
        }
        return response;
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

    public Double hasTheHorseMovedClass(Horse horse, Integer currentRaceClass){
        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getRaceClass() != null)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            if (currentRaceClass != null &&
                    results.get(0).getRaceClass() != null &&
                    results.get(0).getRaceClass() > currentRaceClass) {
                return 1d;
            }
        }
        return 0d;
    }

    public Double checkHorseRecentPerformance(Horse horse, Integer howManyResultsToCheck){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null && result.getPosition() > 0)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                response += ((100 / result.getRunnerCount()) * result.getPosition()) + ((100 / result.getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response == 0){
                return 0d;
            }
            return (100d - (response / count)) / 100;
        }
        return 0d;
    }

    public Double hasTheHorseRanRecently(Horse horse, Integer howManyResultsToCheck){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null && result.getPosition() > 0)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                count++;
                if(raceDayDate.isDateWithing6MonthsOfRaceDate(result.getDate())){
                    response++;
                }
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response == 0){
                return 0d;
            }
            return (100d - (response / count)) / 100;
        }
        return 0d;

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
            return (b / a) * 1d;
        }
        return 0.0;
    }

    public Integer horseRatingBonus(Horse horse){
        if(horse.getOfficialRating() != null) {
            return horse.getOfficialRating() / 100;
        }
        return 0;
    }

    Comparator<Result> horseComparitor = new Comparator<Result>() {
        @Override
        public int compare(Result e1, Result e2) {
            return new Long(e2.getDate().getTimeInMillis()).compareTo(new Long(e1.getDate().getTimeInMillis()));
        }
    };

    Comparator<JockeyTrainerResult> jockeyTrainerResultComparator = new Comparator<JockeyTrainerResult>() {
        @Override
        public int compare(JockeyTrainerResult e1, JockeyTrainerResult e2) {
            return new Long(e2.getResult().getDate().getTimeInMillis()).compareTo(new Long(e1.getResult().getDate().getTimeInMillis()));
        }
    };
}
