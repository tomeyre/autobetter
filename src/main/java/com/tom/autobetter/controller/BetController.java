package com.tom.autobetter.controller;

import com.tom.autobetter.data.RaceDayDate;
import com.tom.autobetter.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BetController {

    @Autowired
    BetService betService;

    private RaceDayDate raceDayDate = RaceDayDate.getInstance();

    @GetMapping(value = "/placeBets/{0}", produces = "application/json")
    public String placeBets(@PathVariable("0") final String date){
        raceDayDate.setCalendar(date);
        return betService.placeBets();
    }

    @GetMapping(value = "/checkWinners/{0}", produces = "application/json")
    public String checkWinners(@PathVariable("0") final String date){
        raceDayDate.setCalendar(date);
        return betService.checkWinners();
    }
}
