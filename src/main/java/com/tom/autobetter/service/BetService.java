package com.tom.autobetter.service;

import com.tom.autobetter.data.Bet;
import com.tom.autobetter.entity.sporting_life.Meet;
import com.tom.autobetter.service.betfair.BetfairService;
import com.tom.autobetter.service.sportinglife.SportingLifeService;
import com.tom.autobetter.util.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class BetService {

    @Autowired
    BetfairService betfairService;

    @Autowired
    SportingLifeService sportingLifeService;

    @Autowired
    SendEmailSMTP sendEmailSMTP;

    public String placeBets(){

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,2020);
//        calendar.set(Calendar.MONTH,3);
//        calendar.set(Calendar.DAY_OF_MONTH,1);
//
//        betfairService.login();
//        List<Meet> raceDay = sportingLifeService.getTheDaysRaces(calendar);
//        List<Bet> betsToPlace = sportingLifeService.workOutBetsToPlace(raceDay, calendar);
//        for(Bet bet : betsToPlace){
//            System.out.println(bet.getRace() + " " + bet.getHorseName() + " " + bet.getId());
//        }
        betfairService.placeBets();
//        sendEmailSMTP.sendMessage(betsToPlace.size(), 0.0, betfairService.getAccountFunds());

        return null;
    }
}
