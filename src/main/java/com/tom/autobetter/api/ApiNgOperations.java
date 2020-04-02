package com.tom.autobetter.api;

import com.tom.autobetter.entity.betfair.*;
import com.tom.autobetter.enums.*;
import com.tom.autobetter.exceptions.APINGException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public abstract class ApiNgOperations {
	protected final String FILTER = "filter";
    protected final String LOCALE = "locale";
    protected final String SORT = "sort";
    protected final String MAX_RESULT = "maxResults";
    protected final String MARKET_IDS = "marketIds";
    protected final String MARKET_ID = "marketId";
    protected final String INSTRUCTIONS = "instructions";
    protected final String CUSTOMER_REF = "customerRef";
    protected final String MARKET_PROJECTION = "marketProjection";
    protected final String PRICE_PROJECTION = "priceProjection";
    protected final String MATCH_PROJECTION = "matchProjection";
    protected final String ORDER_PROJECTION = "orderProjection";
    protected final String WALLET = "wallet";
    protected final String locale = Locale.getDefault().toString();

	public abstract List<EventTypeResult> listEventTypes(MarketFilter filter) throws APINGException;

	public abstract List<MarketBook> listMarketBook(List<String> marketIds, PriceProjection priceProjection, OrderProjection orderProjection,
                                                    MatchProjection matchProjection, String currencyCod) throws APINGException;

    public abstract List<MarketCatalogue> listMarketCatalogue(MarketFilter filter, Set<MarketProjection> marketProjection,
                                                              MarketSort sort, String maxResul) throws APINGException;

	public abstract PlaceExecutionReport placeOrders(String marketId, List<PlaceInstruction> instructions, String customerRef) throws APINGException;

    public abstract Double getAccountFunds(String baseUrl, ApiNgOperation operation) throws APINGException;

    protected abstract String makeRequest(String operation, Map<String, Object> params, String api, String baseApi) throws APINGException;

}

