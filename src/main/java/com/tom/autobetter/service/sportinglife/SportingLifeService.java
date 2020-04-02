package com.tom.autobetter.service.sportinglife;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.autobetter.data.Bet;
import com.tom.autobetter.entity.betfair.MarketCatalogue;
import com.tom.autobetter.entity.betfair.MarketFilter;
import com.tom.autobetter.entity.betfair.RunnerCatalog;
import com.tom.autobetter.entity.sporting_life.Meet;
import com.tom.autobetter.entity.sporting_life.RaceDetails;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SportingLifeService {

    @Autowired
    BetfairService betfairService;

    private static final String RACE_DAY_URL = "https://www.sportinglife.com/api/horse-racing/racing/racecards/";
    private static final String RACE_URL = "https://www.sportinglife.com/api/horse-racing/race/";
    private HttpUtil httpUtil = new HttpUtil();
    private ObjectMapper mapper = new ObjectMapper();

    public List<Meet> getTheDaysRaces(Calendar calendar){

        System.out.println(RACE_DAY_URL + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        String json = httpUtil.getJSONFromUrl(RACE_DAY_URL + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        try {
            return mapper.readValue(json, new TypeReference<List<Meet>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Bet> workOutBetsToPlace(List<Meet> raceDay, Calendar calendar){
        List<Bet> response = new ArrayList();
        MarketFilter marketFilter;
        marketFilter = new MarketFilter();
        Set<String> eventTypeIds = new HashSet<String>();
        eventTypeIds.add("7");
        marketFilter.setEventIds(eventTypeIds);

        List<MarketCatalogue> marketCatalogueList = betfairService.getHorseRacesToday(calendar);
        raceDay.stream().forEach(meet -> meet.getRaces().stream().forEach(race -> {
            try {
                System.out.println(RACE_URL + race.getRaceSummaryReference().getId());
                race.setRaceDetails(mapper.readValue(httpUtil.getJSONFromUrl(RACE_URL + race.getRaceSummaryReference().getId()), new TypeReference<RaceDetails>() {}));
                for(MarketCatalogue marketCatalogue : marketCatalogueList){
                    for(RunnerCatalog runner : marketCatalogue.getRunners()){
                        if(runner.getRunnerName().toLowerCase().contains(race.getRaceDetails().getHorses().get(0).getHorseDetails().getName().toLowerCase())){
                            response.add(new Bet(race.getCourseName(),race.getRaceDetails().getHorses().get(0).getHorseDetails().getName(),runner.getSelectionId()));
                            break;
                        }
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }));
        return response;
    }
}
