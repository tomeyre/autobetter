package com.tom.autobetter.controller;

import com.tom.autobetter.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceBetController {

    @Autowired
    BetService betService;

    @GetMapping(value = "/placeBets", produces = "application/json")
    public String placeBets(){
        return betService.placeBets();
    }
}
