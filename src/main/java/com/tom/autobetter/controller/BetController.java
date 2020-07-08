package com.tom.autobetter.controller;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.service.BetService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BetController {

    @Autowired
    BetService betService;

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    @GetMapping(value = "/wakeUp", produces = "application/json")
    public String placeBets(){
        return "alright fucker im awake!!!";
    }

    @GetMapping(value = "/placeBets/horse/{0}", produces = "application/json")
    public String placeHorseBets(@PathVariable("0") final String date){
        raceDayDate.setCalendar(date);
        return betService.placeHorseBets();
    }

    @GetMapping(value = "/placeBets/football/{0}", produces = "application/json")
    public String placeFootballBets(@PathVariable("0") final String date){
        raceDayDate.setCalendar(date);
        return betService.placeFootballBets();
    }

    @GetMapping(value = "/checkWinners/{0}", produces = "application/json")
    public String checkWinners(@PathVariable("0") final String date){
        raceDayDate.setCalendar(date);
        return betService.checkWinners();
    }
}
