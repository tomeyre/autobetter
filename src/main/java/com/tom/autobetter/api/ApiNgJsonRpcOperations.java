package com.tom.autobetter.api;

import com.tom.autobetter.containers.EventTypeResultContainer;
import com.tom.autobetter.containers.ListMarketBooksContainer;
import com.tom.autobetter.containers.ListMarketCatalogueContainer;
import com.tom.autobetter.containers.PlaceOrdersContainer;
import com.tom.autobetter.data.SessionToken;
import com.tom.autobetter.entity.betfair.*;
import com.tom.autobetter.enums.*;
import com.tom.autobetter.exceptions.APINGException;
import com.tom.autobetter.util.JsonConverter;
import com.tom.autobetter.util.JsonrpcRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.tom.autobetter.util.CommonConstants.*;

@Service
public class ApiNgJsonRpcOperations extends ApiNgOperations {

    public static final String BETTING = "betting/";
    public static final String ACCOUNT = "account/";
    private static ApiNgJsonRpcOperations instance = null;

    private ApiNgJsonRpcOperations(){}

    public static ApiNgJsonRpcOperations getInstance(){
        if (instance == null){
            instance = new ApiNgJsonRpcOperations();
        }
        return instance;
    }

    public List<EventTypeResult> listEventTypes(MarketFilter filter) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(FILTER, filter);
        params.put(LOCALE, locale);
        String result = getInstance().makeRequest(ApiNgOperation.LISTEVENTTYPES.getOperationName(), params, SPORTS_APING_V1_0, BETTING);
//        if(ApiNGDemo.isDebug())
            System.out.println("\nResponse: "+result);

        EventTypeResultContainer container = JsonConverter.convertFromJson(result, EventTypeResultContainer.class);
        if(container.getError() != null)
            throw container.getError().getData().getAPINGException();

        return container.getResult();

    }

    public List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                           MatchProjection matchProjection, String currencyCode) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_IDS, marketIds);
        params.put(PRICE_PROJECTION, priceProjection);
        params.put(ORDER_PROJECTION, orderProjection);
        params.put(MATCH_PROJECTION, matchProjection);
        params.put("currencyCode", currencyCode);
        String result = getInstance().makeRequest(ApiNgOperation.LISTMARKETBOOK.getOperationName(), params, SPORTS_APING_V1_0, BETTING);
//        if(ApiNGDemo.isDebug())
            System.out.println("\nResponse: "+result);

        ListMarketBooksContainer container = JsonConverter.convertFromJson(result, ListMarketBooksContainer.class);

        if(container.getError() != null)
            throw container.getError().getData().getAPINGException();

        return container.getResult();


    }

    public List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                                     MarketSort sort, String maxResult) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(FILTER, filter);
        params.put(SORT, sort);
        params.put(MAX_RESULT, maxResult);
        params.put(MARKET_PROJECTION, marketProjection);
        String result = getInstance().makeRequest(ApiNgOperation.LISTMARKETCATALOGUE.getOperationName(), params, SPORTS_APING_V1_0, BETTING);
//        if(ApiNGDemo.isDebug())
            System.out.println("\nResponse: "+result);

        ListMarketCatalogueContainer container = JsonConverter.convertFromJson(result, ListMarketCatalogueContainer.class);

        if(container.getError() != null)
            throw container.getError().getData().getAPINGException();

        return container.getResult();

    }

    public PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(MARKET_ID, marketId);
        params.put(INSTRUCTIONS, instructions);
        params.put(CUSTOMER_REF, customerRef);
        String result = getInstance().makeRequest(ApiNgOperation.PLACORDERS.getOperationName(), params, SPORTS_APING_V1_0, BETTING);
//        if(ApiNGDemo.isDebug())
            System.out.println("\nResponse: "+result);

        PlaceOrdersContainer container = JsonConverter.convertFromJson(result, PlaceOrdersContainer.class);

        if(container.getError() != null)
            throw container.getError().getData().getAPINGException();

        return container.getResult();

    }

    @Override
    public Double getAccountFunds(String baseUrl, ApiNgOperation operation) throws APINGException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(LOCALE, locale);
        params.put(WALLET, "UK");
        String result = getInstance().makeRequest(ApiNgOperation.GETACCOUNTFUNDS.getOperationName(), params, ACCOUNT_APING_V1_0, ACCOUNT);
//        if(ApiNGDemo.isDebug())
        System.out.println("\nResponse: "+result);

        return new JSONObject(result).getJSONObject("result").getDouble("availableToBetBalance");
    }

    protected String makeRequest(String operation, Map<String, Object> params, String api, String baseApi) {
        String requestString;
        //Handling the JSON-RPC request
        JsonrpcRequest request = new JsonrpcRequest();
        request.setId("1");
        request.setMethod(api + operation);
        request.setParams(params);

        requestString =  JsonConverter.convertToJson(request);
//        if(ApiNGDemo.isDebug())
            System.out.println("\nRequest: "+requestString);

        //We need to pass the "sendPostRequest" method a string in util format:  requestString
        HttpUtil requester = new HttpUtil();
        return requester.sendPostRequestJsonRpc(requestString, operation, baseApi);

       }

}

