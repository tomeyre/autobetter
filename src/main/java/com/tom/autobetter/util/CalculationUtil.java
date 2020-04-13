package com.tom.autobetter.util;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.entity.sporting_life.Horse;
import java.util.Calendar;
import java.util.stream.Collectors;

public class CalculationUtil {

    private RaceDayDate raceDayDate = new RaceDayDate();

    public Integer hasTheHorseWonAnyOfTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults().stream().filter(result -> !futureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 1)).collect(Collectors.toList()).size();
    }

    public Integer hasTheHorseFinishedSecondInTheirLastRaces(Horse horse) {
        return horse.getHorseDetails().getPreviousResults().stream().filter(result -> !futureDate(result.getDate()) && (result.getPosition() != null && result.getPosition() == 2)).collect(Collectors.toList()).size();

    }

//    public Double doesTheJockeyOftenWin(Horse horse) {
//        Double timesFinishedFirst = 0.0;
//        for (int i = 0; i < participant.getJockey().getRecords().size(); i++) {
//            if (participant.getJockey().getRecords().get(i).getPosition() != null && participant.getJockey().getRecords().get(i).getPosition() == 1) {
//                if (i == 0) {
//                    timesFinishedFirst += 5.0;
//                } else if (i == 1 || i == 2) {
//                    timesFinishedFirst += 2.0;
//                } else {
//                    timesFinishedFirst++;
//                }
//            }
//            if (i == 6) break;
//        }
//        return timesFinishedFirst;
//    }
//
//    public Double doesTheTrainerTrainWinningHorses(Horse horse) {
//        Double timesFinishedFirst = 0.0;
//        for (int i = 0; i < participant.getTrainer().getRecords().size(); i++) {
//            if (participant.getTrainer().getRecords().get(i).getPosition() != null && participant.getTrainer().getRecords().get(i).getPosition() == 1) {
//                timesFinishedFirst++;
//            }
//            if (i == 6) break;
//        }
//        return ((participant.getTrainer().getRecords().size() / 100) * timesFinishedFirst) / 10;
//    }
//
//    public Double hasThisJokeyWonWithThisHorseBefore(Horse horse) {
//        for (int i = 0; i < participant.getJockey().getRecords().size(); i++) {
//            if (participant.getHorse().getName().equalsIgnoreCase(participant.getJockey().getRecords().get(i).getName())) {
//                if (participant.getJockey().getRecords().get(i).getPosition() != null && participant.getJockey().getRecords().get(i).getPosition() == 1) {
//                    return 5.0;
//                }
//                if (participant.getJockey().getRecords().get(i).getPosition() != null &&
//                        participant.getJockey().getRecords().get(i).getPosition() <= 3 &&
//                        participant.getJockey().getRecords().get(i).getPosition() >= 1) {
//                    return 3.0;
//                }
//                return 1.0;
//            }
//            if (i == 6) break;
//        }
//        return 0.0;
//    }
//
//    public Double hasTheHorseRacedInThisClassBeforeAndWon(Horse horse) {
//        boolean hasRacedAtThisClass = false;
//        boolean top3 = false;
//        for (int i = 0; i < participant.getJockey().getRecords().size(); i++) {
//            if (participant.getJockey().getRecords().get(i).getRaceClass().equals(participant.getHorse().getRaceClass())) {
//                hasRacedAtThisClass = true;
//                if (participant.getJockey().getRecords().get(i).getPosition() == 1) {
//                    return 3.0;
//                } else if (participant.getJockey().getRecords().get(i).getPosition() > 1 && participant.getJockey().getRecords().get(i).getPosition() <= 3) {
//                    top3 = true;
//                }
//            }
//            if (i == 6) break;
//        }
//        if (top3) {
//            return 2.0;
//        } else if (hasRacedAtThisClass) {
//            return 1.0;
//        }
//        return 0.0;
//    }
//
//    public Double hasTheHorseRacedWithThisWeightBeforeAndWon(Horse horse) {
//        boolean hasRacedAtThisWeight = false;
//        boolean top3 = false;
//        int count = 0;
//        for (Record record : participant.getHorse().getRecords()) {
//            if (count < 5) {
//                if (record.getWeight().equals(participant.getHorse().getHandicap())) {
//                    hasRacedAtThisWeight = true;
//                    if (record.getPosition() == 1) {
//                        return 3.0;
//                    } else if (record.getPosition() > 1 && record.getPosition() <= 3) {
//                        top3 = true;
//                    }
//                }
//            } else {
//                break;
//            }
//            count++;
//        }
//        if (top3) {
//            return 2.0;
//        } else if (hasRacedAtThisWeight) {
//            return 1.0;
//        }
//        return 0.0;
//    }
//
//    public Double isTheHorseComfortableAtThisDistance(Horse horse) {
//        Double count = 0.0;
//        for (Record record : participant.getHorse().getRecords()) {
//            if (record.getDistance().equalsIgnoreCase(raceStats.getDistance())) {
//                if (record.getPosition() == 1) {
//                    count += 2.0;
//                } else if (record.getPosition() == 2) {
//                    count += 1.0;
//                }
//            }
//        }
//        return count;
//    }
//
//    public Double isTheHorseReliable(Horse horse) {
//        Double racesFinished = 0.0;
//        for (int i = 0; i < participant.getHorse().getRecords().size(); i++) {
//            if (participant.getHorse().getRecords().get(i).getPosition() != null &&
//                    participant.getHorse().getRecords().get(i).getPosition() > 0) {
//                racesFinished++;
//            }
//            if (i == 6) break;
//        }
//        return racesFinished;
//    }
//
//    public Double isTheJockeyReliable(Horse horse) {
//        Double racesFinished = 0.0;
//        for (int i = 0; i < participant.getJockey().getRecords().size(); i++) {
//            if (participant.getJockey().getRecords().get(i).getPosition() != null &&
//                    participant.getJockey().getRecords().get(i).getPosition() > 0) {
//                racesFinished++;
//            }
//            if (i == 6) break;
//        }
//        return racesFinished;
//    }
//
//    public Double doesTheHorseFavourThisGround(Horse horse) {
//        Double favoredGround = 0.0;
//        int i = 0;
//        if (!participant.getHorse().getGoing().equalsIgnoreCase("")) {
//            for (Record record : participant.getHorse().getRecords()) {
//                if (record.getGoing().equalsIgnoreCase(participant.getHorse().getGoing())) {
//                    if (record.getPosition() == 1 || record.getPosition() == 2) {
//                        favoredGround++;
//                    }
//                }
//                if (i == 6) break;
//                i++;
//            }
//        }
//        return favoredGround;
//    }
//
//    public Double hasTheLast6RacesBeenRecent(Horse horse) {
//        long days;
//        Double racesOften = 0.0;
//        if (participant.getHorse().getRecords().isEmpty()) {
//            return 0.0;
//        } else {
//            for (Record record : participant.getHorse().getRecords()) {
//                days = new Date().getTime() - record.getDate().getTime() / (1000 * 60 * 60 * 24);
//                racesOften += days < 30 ? 1.0 : 0.0;
//            }
//        }
//        return racesOften;
//    }
//
//    public Double hasTheHorseMovedClass(Horse horse) {
//        if (participant.getHorse().getRecords().isEmpty()) {
//            return 0.0;
//        }
//        if ((participant.getHorse().getRecords().get(0).getRaceClass() < participant.getHorse().getRaceClass()) && participant.getHorse().getRaceClass() != 0 && participant.getHorse().getRecords().get(0).getRaceClass() != 0) {
//            return 3.0;
//        }
//        if ((participant.getHorse().getRecords().get(0).getRaceClass() == participant.getHorse().getRaceClass()) && participant.getHorse().getRaceClass() != 0 && participant.getHorse().getRecords().get(0).getRaceClass() != 0) {
//            return 2.0;
//        }
//        if ((participant.getHorse().getRecords().get(0).getRaceClass() > participant.getHorse().getRaceClass()) && participant.getHorse().getRaceClass() != 0 && participant.getHorse().getRecords().get(0).getRaceClass() != 0) {
//            return 1.0;
//        }
//        return 0.0;
//    }
//
//    public Double horseRatingBonus(Horse horse) {
//        Double ratingBonus = 0.0;
//        for (int i = 0; i < participants.size(); i++) {
//            if (participant.getHorse().getOfficialRating() >= participants.get(i).getHorse().getOfficialRating()) {
//                ratingBonus++;
//            }
//        }
//        return participant.getHorse().getOfficialRating().doubleValue() / 10;
//    }
//
//    public Double horseOdds(Horse horse) {
//        String[] odds = participant.getHorse().getOdds().split("/");
//        if (Character.isDigit(odds[0].charAt(0))) {
//            return ((Double.parseDouble(odds[1]) + 0.0) / (Double.parseDouble(odds[0]) + 0.0)) * 10;
//        }
//        return 10.0;
//    }

    private Boolean futureDate(Calendar resultDate){
        if(resultDate.get(Calendar.YEAR) > raceDayDate.getYear()){
            return true;
        }
        if(resultDate.get(Calendar.MONTH) > raceDayDate.getMonth()){
            return true;
        }
        if(resultDate.get(Calendar.DAY_OF_MONTH) > raceDayDate.getDayOfMonth()){
            return true;
        }
        return false;
    }
}
