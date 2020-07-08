package com.tom.autobetter.util;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.sporting_life.horse.Horse;
import com.tom.autobetter.entity.sporting_life.horse.JockeyTrainerResult;
import com.tom.autobetter.entity.sporting_life.horse.RaceSummary;
import com.tom.autobetter.entity.sporting_life.horse.Result;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CalculationUtil {

    public static final double DEFAULT = 1.5d;
    public static final double TOTAL = 3d;
    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    public Double hasTheHorseWonAnyOfTheirLastRaces(Horse horse, Integer howManyResultsToCheck) {
        int count = 0;
        Double response = 0d;

        if (horse.getJockey().getResults() == null) {
            return 0.0;
        }
        List<Result> results = horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null && result.getPosition() > 0)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                response += result.getPosition() == 1 ? 1 : 0;
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                print("horse wins : ", ((100 / count) * response) / 100, null);
                return ((100 / count) * response) / 100;
            }
        }
        print("horse wins : ", 0d, null);
        return DEFAULT;
    }

    private void print(String type, Double input, Integer intput){
        System.out.println(type + (input != null ? input : intput));
    }

    public Double checkJockeyRecentPerformance(Horse horse, Integer howManyResultsToCheck){
        int count = 0;
        Double response = 0d;

        if (horse.getJockey().getResults() == null) {
            print("jockey wins : ", 0d, null);
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
            if(response != 0) {
                Double result = ((100 * count) / response);
                print("jockey wins : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("jockey wins : ", 0d, null);
        return DEFAULT;
    }

    public Double hasThisJockeyWonWithThisHorseBeforeRecently(Horse horse, Integer howManyResultsToCheck) {
        Double response = 0d;
        int count = 0;
        if (horse.getJockey().getResults() == null) {
            print("jockey + horse wins : ", 0d, null);
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
            if(response != 0d) {
                Double result = ((100 * count) / response);
                print("jockey + horse wins : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("jockey + horse wins : ", 0d, null);
        return response;
    }

    public Double checkHorseRecentPerformance(Horse horse, Integer howManyResultsToCheck){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null)
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                int pos = result.getPosition() == 0 ? result.getRunnerCount() : result.getPosition();
                response += ((100 / result.getRunnerCount()) * pos) + ((100 / result.getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                Double result = ((100 * count) / response);
                print("horse performance : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("horse performance : ", 0d, null);
        return DEFAULT;
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
                if(raceDayDate.isDateWithingXMonthsOfRaceDate(result.getDate(), 12)){
                    response++;
                }
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                print("recent runs : ", ((100 / count) * response) / 100, null);
                return ((100 / count) * response) / 100;
            }
        }
        print("recent runs : ", 0d, null);
        return DEFAULT;

    }

    public Double horseOdds(Horse horse){
        if(Character.isDigit(horse.getBetting().getCurrentOdds().charAt(0))) {
            int a = Integer.parseInt(horse.getBetting().getCurrentOdds().split("/")[0]);
            int b = Integer.parseInt(horse.getBetting().getCurrentOdds().split("/")[1]);
            System.out.println("odds : " + ((b / a) * 1d));
            return (b / a) * 1d;
        }
        System.out.println("odds : 0.0");
        return 0.0;
    }

    public Integer horseRatingBonus(Horse horse){
        if(horse.getOfficialRating() != null) {
            System.out.println("rating : " + (horse.getOfficialRating() / 100));
            return horse.getOfficialRating() / 100;
        }
        System.out.println("rating : 0.0");
        return 0;
    }



    public Double performanceAtThisDistance(Horse horse, Integer howManyResultsToCheck, RaceSummary race){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null &&
                        race.getDistance().equalsIgnoreCase(result.getDistance()))
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                int pos = result.getPosition() == 0 ? result.getRunnerCount() : result.getPosition();
                response += ((100 / result.getRunnerCount()) * pos) + ((100 / result.getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                Double result = ((100 * count) / response);
                print("distance performance : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("distance performance : ", 0d, null);
        return DEFAULT;
    }

    public Double performanceAtThisGoing(Horse horse, Integer howManyResultsToCheck, RaceSummary race){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null &&
                        race.getGoing().equalsIgnoreCase(result.getGoing()))
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            for(Result result : results) {
                int pos = result.getPosition() == 0 ? result.getRunnerCount() : result.getPosition();
                response += ((100 / result.getRunnerCount()) * pos) + ((100 / result.getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                Double result = ((100 * count) / response);
                print("going performance : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("going performance : ", 0d, null);
        return DEFAULT;
    }

    public Double performanceAtThisClass(Horse horse, Integer howManyResultsToCheck, RaceSummary race){
        int count = 0;
        Double response = 0d;

        List<Result> results =  horse.getHorseDetails().getPreviousResults()
                .stream()
                .filter(result -> !raceDayDate.todayOrFutureDate(result.getDate()) && result.getPosition() != null &&
                        race.getRaceClass() == result.getRaceClass())
                .collect(Collectors.toList());

        if (!results.isEmpty() && results.size() > 0) {
            results.sort(horseComparitor);
            if (race.getRaceClass() != null && results.get(0).getRaceClass() > race.getRaceClass()){
                response += 0.20d;
            } else if(race.getRaceClass() != null && results.get(0).getRaceClass() == race.getRaceClass()){
                response += 0.10d;
            }
            for(Result result : results) {
                int pos = result.getPosition() == 0 ? result.getRunnerCount() : result.getPosition();
                response += ((100 / result.getRunnerCount()) * pos) + ((100 / result.getRunnerCount()) / 2);
                count++;
                if (count == howManyResultsToCheck) {
                    break;
                }
            }
            if(response != 0) {
                Double result = ((100 * count) / response);
                print("race type performance : ", count <= 3 && result > TOTAL ? result / 2 : result, null);
                return count <= 3 && result > TOTAL ? result / 2 : result;
            }
        }
        print("race type performance : ", 0d, null);
        return DEFAULT;
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
