package com.tom.autobetter.api;

import com.google.gson.reflect.TypeToken;
import com.tom.autobetter.entity.betfair.*;
import com.tom.autobetter.enums.*;
import com.tom.autobetter.exceptions.APINGException;
import com.tom.autobetter.util.JsonConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ApiNgRescriptOperations extends ApiNgOperations {


    public static final String BETTING = "betting/";
    public static final String ACCOUNT = "account/";

    private static ApiNgRescriptOperations instance = null;

    private ApiNgRescriptOperations(){}

    public static ApiNgRescriptOperations getInstance(){
        if (instance == null){
            instance = new ApiNgRescriptOperations();
        }
        return instance;
    }

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put(LOCALE, locale);
        String result = getInstance().makeRequest(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params,null, BETTING);
//        if(ApiNGDemo.isDebug())
//            System.out.println("\nResponse: "+result);

        List<EventTypeResult> container = JsonConverter.convertFromJson(result, new TypeToken<List<EventTypeResult>>() {}.getType());

        return container;

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                           MatchProjection matchProjection, String currencyCod) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_IDS, marketIds);
        params.put(PRICE_PROJECTION, priceProjection);
        params.put(ORDER_PROJECTION, orderProjection);
        params.put(MATCH_PROJECTION, matchProjection);
        String result = getInstance().makeRequest(ApiNgOperation.LISTMARKETBOOK.getOperationName(), params,null, BETTING);
//        if(ApiNGDemo.isDebug())
//            System.out.println("\nResponse: "+result);

        List<MarketBook> container = JsonConverter.convertFromJson(result, new TypeToken<List<MarketBook>>(){}.getType() );

        return container;

    }

    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                                     MarketSort sort, String maxResult) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(FILTER, filter);
        params.put(SORT, sort);
        params.put(MAX_RESULT, maxResult);
        params.put(MARKET_PROJECTION, marketProjection);
        String result = getInstance().makeRequest(ApiNgOperation.LISTMARKETCATALOGUE.getOperationName(), params,null, BETTING);
//        if(ApiNGDemo.isDebug())
//            System.out.println("\nResponse: "+result);

        List<MarketCatalogue> container = JsonConverter.convertFromJson(result, new TypeToken< List<MarketCatalogue>>(){}.getType() );

        return container;

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = getInstance().makeRequest(ApiNgOperation.PLACORDERS.getOperationName(), params,null, BETTING);
//        if(ApiNGDemo.isDebug())
//            System.out.println("\nResponse: "+result);

        return JsonConverter.convertFromJson(result, PlaceExecutionReport.class);

    }

    @Override
    public Double getAccountFunds(String baseUrl, ApiNgOperation operation) throws APINGException {
        return null;
    }


    protected String makeRequest(String operation, Map<String, Object> params, String api, String baseApi) throws APINGException {
        String requestString;
        //Handling the Rescript request
        params.put("id", 1);

        requestString =  JsonConverter.convertToJson(params);
//        if(ApiNGDemo.isDebug())
//            System.out.println("\nRequest: "+requestString);

        //We need to pass the "sendPostRequest" method a string in util format:  requestString
        HttpUtil requester = new HttpUtil();
        String response = requester.sendPostRequestRescript(requestString, operation, baseApi);
        if(response != null)
            return response;
        else
            throw new APINGException();
    }

}

