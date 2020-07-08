package com.tom.autobetter.service.sportinglife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.FootballTeamResult;
import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.data.Winner;
import com.tom.autobetter.entity.betfair.MarketFilter;
import com.tom.autobetter.entity.dynamodb.Event;
import com.tom.autobetter.entity.dynamodb.Race;
import com.tom.autobetter.entity.dynamodb.RaceDayEntity;
import com.tom.autobetter.entity.sporting_life.football.Competition;
import com.tom.autobetter.entity.sporting_life.football.FootballMatch;
import com.tom.autobetter.entity.sporting_life.football.Team;
import com.tom.autobetter.entity.sporting_life.football.TeamResult;
import com.tom.autobetter.entity.sporting_life.horse.*;
import com.tom.autobetter.repository.dynamodb.AutobetterRepository;
import com.tom.autobetter.util.CalculationUtil;
import com.tom.autobetter.util.CommonConstants;
import com.tom.autobetter.util.HttpUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.tom.autobetter.util.CommonConstants.WIN;

@Service
public class SportingLifeService {

//    @Autowired
//    private BetfairService betfairService;

    @Autowired
    AutobetterRepository autobetterRepository;

    private CalculationUtil calculationUtil = new CalculationUtil();
    private RaceDayDate raceDayDate = RaceDayDate.getInstance();
    private static final String RACE_DAY_URL = "https://www.sportinglife.com/api/horse-racing/racing/racecards/";
    private static final String FOOTBALL_MATCHES_BY_DAY_URL = "https://www.sportinglife.com/api/football/match_day?match_date=";
    private static final String FOOTBALL_TEAM_BY_ID_URL = "https://www.sportinglife.com/api/football/team/";//https://www.sportinglife.com/api/football/form?match_id=";
    private static final String FOOTBALL_COMPETITION_BY_ID_URL = "https://www.sportinglife.com/api/football/competition/";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";
    private static final String HORSE_URL = "https://www.sportinglife.com/api/horse-racing/horse/";
    private static final String JOCKEY_URL = "https://www.sportinglife.com/api/horse-racing/jockey/";
    private static final String TRAINER_URL = "https://www.sportinglife.com/api/horse-racing/trainer/";
    private HttpUtil httpUtil = new HttpUtil();
    private ObjectMapper mapper = new ObjectMapper();

    public List<Meet> getTheDaysRaces() {

        System.out.println(RACE_DAY_URL + raceDayDate.getYear() + "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        String json = httpUtil.getJSONFromUrl(RACE_DAY_URL + raceDayDate.getYear() + "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        try {
            return mapper.readValue(json, new TypeReference<List<Meet>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RaceDayEntity workOutBetsToPlace(List<Meet> raceDay) {
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
                    race.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + race.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {
                    }));

                    for (Horse horse : race.getRaceDetails().getHorses()) {
                        if (horse.getHorseDetails().getPreviousResults() != null && horse.getHorseDetails().getPreviousResults().size() == 6) {
                            System.out.println(HORSE_URL + horse.getHorseDetails().getHorseReference().getId());
                            horse.getHorseDetails().setPreviousResults(mapper.readValue(new JSONObject(httpUtil.getJSONFromUrl(HORSE_URL + horse.getHorseDetails().getHorseReference().getId())).getJSONArray("previous_results").toString(), new TypeReference<List<Result>>() {
                            }));
                        }
                        if (horse.getJockey() != null && horse.getJockey().getJockeyReference() != null) {
                            System.out.println(JOCKEY_URL + horse.getJockey().getJockeyReference().getId());
                            horse.setJockey(mapper.readValue(httpUtil.getJSONFromUrl(JOCKEY_URL + horse.getJockey().getJockeyReference().getId()), new TypeReference<Jockey>() {
                            }));
                        }
//                        System.out.println(TRAINER_URL + horse.getTrainer().getTrainerReference().getId());
//                        horse.setTrainer(mapper.readValue(httpUtil.getJSONFromUrl(TRAINER_URL + horse.getTrainer().getTrainerReference().getId()), new TypeReference<Trainer>() {}));
                    }

                    Winner winner = workOutRaceWinner(race);
                    System.out.println(winner.getName() + " :: " + winner.getFinishPosition());

                    List<Race> raceList = response.getEvents().stream().filter(event -> event.getEventId() == meet.getMeetingSummary().getMeetingReference().getId()).findFirst().get().getRaces();
                    raceList.add(createNewRace(race, winner/*, runner.getSelectionId()*/));
                    response.getEvents().stream().filter(event -> event.getEventId() == meet.getMeetingSummary().getMeetingReference().getId()).findFirst().get().setRaces(raceList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return response;
    }

    public List<FootballMatch> getFootballMatchesForDate(){
        System.out.println(FOOTBALL_MATCHES_BY_DAY_URL + raceDayDate.getYear() + "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        String json = httpUtil.getJSONFromUrl(FOOTBALL_MATCHES_BY_DAY_URL + raceDayDate.getYear() + "-" + (raceDayDate.getMonth() + 1) + "-" + raceDayDate.getDayOfMonth());
        try {
            return mapper.readValue(json, new TypeReference<List<FootballMatch>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FootballTeamResult getFootballMatchDetailsById(Integer id, String vs){
        System.out.println(FOOTBALL_TEAM_BY_ID_URL + id);
        String json = httpUtil.getJSONFromUrl(FOOTBALL_TEAM_BY_ID_URL + id);
        try {
            mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            TeamResult teamResult = mapper.readValue(json, new TypeReference<TeamResult>() {});
            return getResults(teamResult, vs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FootballTeamResult getResults(TeamResult teamResult, String vs){
        if(teamResult.getMatch().isEmpty() || teamResult.getMatch().size() == 0) return new FootballTeamResult(teamResult.getTeam().getName(), 0d, vs);
        String teamToCheck = teamResult.getTeam().getName();
        FootballMatch matchResult = teamResult.getMatch().stream().filter(match -> raceDayDate.isTodaysDate(match.getMatchDate())).findFirst().orElse(null);
        teamResult.setMatch(teamResult.getMatch().stream().filter(match -> !raceDayDate.todayOrFutureDate(match.getMatchDate())).collect(Collectors.toList()));
        Double score = 0d;
        Double wins = 0d;
        Double totalMatchesAgainstMatchedTeam = 0d;
        Double winsAgainstMatchedTeam = 0d;
        Double recentWins = 0d;
        teamResult.getMatch().sort(matchComparator);
        int recentTotal = 10;
        int count = 0;
        for(FootballMatch match : teamResult.getMatch()){
            count++;
            if(match.getMatchOutcome() != null && match.getMatchOutcome().getOutcome().equalsIgnoreCase(WIN)){
                if(match.getTeamScoreA().getTeam().getName().equalsIgnoreCase(teamToCheck) &&
                match.getMatchOutcome().getWinner().getName().equalsIgnoreCase(match.getTeamScoreA().getTeam().getName())){
                    wins++;
                    if(count < recentTotal) {
                        recentWins++;
                    }
                }
                else if(match.getTeamScoreB().getTeam().getName().equalsIgnoreCase(teamToCheck) &&
                        match.getMatchOutcome().getWinner().getName().equalsIgnoreCase(match.getTeamScoreB().getTeam().getName())){
                    wins++;
                    if(count < recentTotal) {
                        recentWins++;
                    }
                }
                if(match.getTeamScoreA().getTeam().getName().equalsIgnoreCase(vs) &&
                        match.getMatchOutcome().getWinner().getName().equalsIgnoreCase(match.getTeamScoreA().getTeam().getName())){
                    winsAgainstMatchedTeam++;
                    totalMatchesAgainstMatchedTeam++;
                }
                else if(match.getTeamScoreB().getTeam().getName().equalsIgnoreCase(vs) &&
                        match.getMatchOutcome().getWinner().getName().equalsIgnoreCase(match.getTeamScoreB().getTeam().getName())){
                    winsAgainstMatchedTeam++;
                    totalMatchesAgainstMatchedTeam++;
                } else if (match.getTeamScoreA().getTeam().getName().equalsIgnoreCase(vs) || match.getTeamScoreB().getTeam().getName().equalsIgnoreCase(vs)){
                    totalMatchesAgainstMatchedTeam++;
                }
            }else{
                if (match.getTeamScoreA().getTeam().getName().equalsIgnoreCase(vs) || match.getTeamScoreB().getTeam().getName().equalsIgnoreCase(vs)){
                    totalMatchesAgainstMatchedTeam++;
                }
            }
        }

        if(matchResult != null && matchResult.getMatchOutcome() != null) {
            Double compRank = getCompRank(matchResult, vs);
            System.out.println("Rank Score : " + compRank);
            score += compRank;
        }
        if(teamResult.getMatch().size() > 0) {
            System.out.println(teamResult.getTeam().getName());
            System.out.println("wins " + wins);
            score += wins;
            System.out.println("wins against matched team " + winsAgainstMatchedTeam);
            score += winsAgainstMatchedTeam;
            if (count > 0) {
                System.out.println("recent wins " + recentWins);
                score += recentWins;
            }
        }

        FootballTeamResult response = new FootballTeamResult();
        response.setScore(score);
        if(matchResult != null && matchResult.getMatchOutcome() != null){
            response.setWinner(matchResult.getMatchOutcome().getOutcome().equalsIgnoreCase(WIN) ? matchResult.getMatchOutcome().getWinner().getName() : "DRAW");
        }else{
            response.setWinner("UNKOWN");
        }
        return response;
    }

    private Double getCompRank(FootballMatch footballMatch, String vs) {
        try {
            System.out.println(FOOTBALL_COMPETITION_BY_ID_URL + footballMatch.getCompetition().getCompetitionReference().getId());
            Competition competition = (mapper.readValue(httpUtil.getJSONFromUrl(FOOTBALL_COMPETITION_BY_ID_URL + footballMatch.getCompetition().getCompetitionReference().getId()), new TypeReference<Competition>() {}));
            for(int i = 0; i < competition.getTeams().size(); i++){
                if(competition.getTeams().get(i).getName().equalsIgnoreCase(footballMatch.getTeamScoreA().getTeam().getName()) && !footballMatch.getTeamScoreA().getTeam().getName().equalsIgnoreCase(vs)){
                    System.out.println("Rank : " + i);
                    return 0d + competition.getTeams().size() - i;
                }else if(competition.getTeams().get(i).getName().equalsIgnoreCase(footballMatch.getTeamScoreB().getTeam().getName()) && !footballMatch.getTeamScoreB().getTeam().getName().equalsIgnoreCase(vs)){
                    System.out.println("Rank : " + i);
                    return 0d + competition.getTeams().size() - i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0d;
    }

    Comparator<FootballMatch> matchComparator = new Comparator<FootballMatch>() {
        @Override
        public int compare(FootballMatch e1, FootballMatch e2) {
            return new Long(e2.getMatchDate().getTime()).compareTo(new Long(e1.getMatchDate().getTime()));
        }
    };

    private Race createNewRace(RaceSummary race, Winner winner/*, Long betId*/) {
        Race raceEntity = new Race();
        raceEntity.setCorrect(null);
        raceEntity.setHorseName(winner.getName());
        raceEntity.setOdds(winner.getOdds());
        raceEntity.setRace(race.getCourseName());
        raceEntity.setRaceId(race.getRaceSummaryReference().getId());
        raceEntity.setBetId(1l);
        raceEntity.setRaceTime(race.getTime());
        raceEntity.setClearWinner(winner.getClearWinner());
        raceEntity.setScore(winner.getScore());
        return raceEntity;
    }

    private Event createNewEvent(Meet meet) {
        Event event = new Event();
        event.setEventId(meet.getMeetingSummary().getMeetingReference().getId());
        event.setEventName(meet.getMeetingSummary().getCourse().getName());
        List<Race> races = new ArrayList<>();
        event.setRaces(races);
        return event;
    }

    private Winner workOutRaceWinner(RaceSummary race) {
        List<Winner> rankings = new ArrayList<>();
        for (Horse horse : race.getRaceDetails().getHorses()) {
            Winner temp = new Winner();
            temp.setName(horse.getHorseDetails().getName());
            temp.setOdds(horse.getBetting().getCurrentOdds());
            if (horse.getRunning()) {
                temp.setScore(calculateChanceOfWinning(horse, race));
            } else {
                temp.setScore(-1d);
            }
            if (horse.getFinishingPosition() != null) {
                temp.setFinishPosition(horse.getFinishingPosition());
            }
            rankings.add(temp);
        }
        rankings.sort(horseComparitor);
        int count = 1;
        Double score = 0d;
        for (Winner winner : rankings) {
            if (score == 0) {
                score = winner.getScore();
            } else if (score == winner.getScore()) {
                count++;
            }
        }
//        System.out.println("potential winners for race : " + count);
//        System.out.println(rankings.get(0).getFinishPosition());
        rankings.get(0).setClearWinner(rankings.get(0).getScore() - rankings.get(1).getScore() > 1);
        rankings.get(0).setScore(rankings.get(0).getScore() - rankings.get(1).getScore());
        return rankings.get(0);
    }

    Comparator<Winner> horseComparitor = new Comparator<Winner>() {
        @Override
        public int compare(Winner e1, Winner e2) {
            return e2.getScore().compareTo(e1.getScore());
        }
    };

    private Double workoutOddsPercentage(String odds) {
        if (odds != null && !odds.equalsIgnoreCase("") && Character.isDigit(odds.charAt(0))) {
            Double a = Double.parseDouble(odds.split("/")[0]);
            Double b = Double.parseDouble(odds.split("/")[1]);
            return 1 / (a / b);
        }
        return 0.0;
    }

    private Double calculateChanceOfWinning(Horse horse, RaceSummary race) {
        Double score = 0.0;
        System.out.println("-----------------------------------");
        score += calculationUtil.hasTheHorseWonAnyOfTheirLastRaces(horse, 6).doubleValue();
        score += calculationUtil.hasThisJockeyWonWithThisHorseBeforeRecently(horse, 6).doubleValue();
        score += calculationUtil.checkHorseRecentPerformance(horse, 6);
        score += calculationUtil.performanceAtThisDistance(horse, 6, race);
        score += calculationUtil.performanceAtThisGoing(horse, 6, race);
        score += calculationUtil.performanceAtThisClass(horse, 6, race);
//        score += calculationUtil.hasTheHorseRanRecently(horse, 6);
//        if (score == 1.5) {
            score += calculationUtil.checkJockeyRecentPerformance(horse, 6);
//            score += calculationUtil.horseRatingBonus(horse);
//            score += calculationUtil.horseOdds(horse);
//            score /= 3d;

//        }

//        score += calculationUtil.hasTheHorseMovedClass(horse, raceClass);
        System.out.println(horse.getHorseDetails().getName() + " Overall score " + score + " finished : " + horse.getFinishingPosition() + "-----------------------------------");
        return score;
    }
}
