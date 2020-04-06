package com.tom.autobetter.service.betfair;

import com.tom.autobetter.api.ApiNgJsonRpcOperations;
import com.tom.autobetter.api.ApiNgOperations;
import com.tom.autobetter.data.SessionToken;
import com.tom.autobetter.dynamodb.AutobetterRepository;
import com.tom.autobetter.entity.betfair.*;
import com.tom.autobetter.enums.*;
import com.tom.autobetter.exceptions.APINGException;
import com.tom.autobetter.util.HttpUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.tom.autobetter.enums.ApiNgOperation.GETACCOUNTFUNDS;
import static com.tom.autobetter.util.CommonConstants.ACCOUNT_APING_V1_0;

@Service
public class BetfairService {

    private ApiNgOperations jsonOperations = ApiNgJsonRpcOperations.getInstance();
    private SessionToken sessionToken = SessionToken.getInstance();
    private HttpUtil httpUtil = new HttpUtil();

    public void login(){
//        if(sessionToken.getSsoId() == null) {
            JSONObject body = new JSONObject();
            body.put("username", "tom.eyre8770@gmail.com").put("password", System.getenv("PASS_TWO"));
            sessionToken.setSsoId(httpUtil.loginToBetfair("https://identitysso-cert.betfair.com/api/certlogin", body.toString()));
//        }
    }

    public List<MarketCatalogue> getHorseRacesToday(Calendar calendar) {

        try {
            MarketFilter marketFilter;
            marketFilter = new MarketFilter();
            Set<String> eventTypeIds = new HashSet<String>();
            Integer maxResults = 1;

            List<EventTypeResult> r = jsonOperations.listEventTypes(marketFilter);
            for (EventTypeResult eventTypeResult : r) {
                if(eventTypeResult.getEventType().getName().equals("Horse Racing")){
                    eventTypeIds.add(eventTypeResult.getEventType().getId());
                    maxResults = eventTypeResult.getMarketCount();
                    System.out.println(eventTypeResult.getEventType() + " " + eventTypeResult.getMarketCount());
                }
            }

            System.out.println("4.(listMarketCataloque) Get next horse racing market in the UK...\n");
            TimeRange time = new TimeRange();
            Date from = new Date();
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            from.setTime(calendar.getTimeInMillis());
            time.setFrom(from);
            Date to = new Date();
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            to.setTime(calendar.getTimeInMillis());
            time.setTo(to);

            Set<String> countries = new HashSet<String>();
            countries.add("US");

            Set<String> typesCode = new HashSet<String>();
            typesCode.add("WIN");

            marketFilter = new MarketFilter();
            marketFilter.setEventTypeIds(eventTypeIds);
            marketFilter.setMarketStartTime(time);
//            marketFilter.setMarketCountries(countries);
            marketFilter.setMarketTypeCodes(typesCode);

            Set<MarketProjection> marketProjection = new HashSet<MarketProjection>();
            marketProjection.add(MarketProjection.RUNNER_METADATA);

            return jsonOperations.listMarketCatalogue(marketFilter, marketProjection, MarketSort.FIRST_TO_START, maxResults.toString());
        } catch (APINGException apiExc) {
            System.out.println(apiExc.toString());
        }
        return null;
    }

    public Double getAccountFunds(){
        try{
            return jsonOperations.getAccountFunds(ACCOUNT_APING_V1_0, GETACCOUNTFUNDS);
        } catch (APINGException apiExc) {
            System.out.println(apiExc.toString());
        }
        return null;
    }

    @Autowired
    AutobetterRepository autobetterRepository;

    public void placeBets() {
        try {
            System.out.println("starting dynamoDb");
            autobetterRepository.findAllById(1);
        }catch (Exception e){
            System.out.println("Error with dynamoDb");
            e.printStackTrace();
        }
//            /**
//             * PlaceOrders: we try to place a bet, based on the previous request we provide the following:
//             * marketId: the market id
//             * selectionId: the runner selection id we want to place the bet on
//             * side: BACK - specify side, can be Back or Lay
//             * orderType: LIMIT - specify order type
//             * size: the size of the bet
//             * price: the price of the bet
//             * customerRef: 1 - unique reference for a transaction specified by user, must be different for each request
//             *
//             */
//
//            long selectionId = 0;
//            if ( marketBookReturn.size() != 0 ) {
//                Runner runner = marketBookReturn.get(0).getRunners().get(0);
//                selectionId = runner.getSelectionId();
//                System.out.println("7. Place a bet below minimum stake to prevent the bet actually " +
//                        "being placed for marketId: "+marketIdChosen+" with selectionId: "+selectionId+"...\n\n");
//                List<PlaceInstruction> instructions = new ArrayList<PlaceInstruction>();
//                PlaceInstruction instruction = new PlaceInstruction();
//                instruction.setHandicap(0);
//                instruction.setSide(Side.BACK);
//                instruction.setOrderType(OrderType.LIMIT);
//
//                LimitOrder limitOrder = new LimitOrder();
//                limitOrder.setPersistenceType(PersistenceType.LAPSE);
//                //API-NG will return an error with the default size=0.01. This is an expected behaviour.
//                //You can adjust the size and price value in the "apingdemo.properties" file
//                limitOrder.setPrice(getPrice());
//                limitOrder.setSize(getSize());
//
//                instruction.setLimitOrder(limitOrder);
//                instruction.setSelectionId(selectionId);
//                instructions.add(instruction);
//
//                String customerRef = "1";
//
//                PlaceExecutionReport placeBetResult = jsonOperations.placeOrders(marketIdChosen, instructions, customerRef, applicationKey, sessionToken);
//
//                // Handling the operation result
//                if (placeBetResult.getStatus() == ExecutionReportStatus.SUCCESS) {
//                    System.out.println("Your bet has been placed!!");
//                    System.out.println(placeBetResult.getInstructionReports());
//                } else if (placeBetResult.getStatus() == ExecutionReportStatus.FAILURE) {
//                    System.out.println("Your bet has NOT been placed :*( ");
//                    System.out.println("The error is: " + placeBetResult.getErrorCode() + ": " + placeBetResult.getErrorCode().getMessage());
//                }
//            } else {
//                System.out.println("Sorry, no runners found\n\n");
//            }
//
    }
}
