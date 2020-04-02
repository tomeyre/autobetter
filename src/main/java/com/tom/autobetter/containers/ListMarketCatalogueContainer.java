package com.tom.autobetter.containers;


import com.tom.autobetter.entity.betfair.MarketCatalogue;

import java.util.List;

public class ListMarketCatalogueContainer extends Container {

	private List<MarketCatalogue> result;

	public List<MarketCatalogue> getResult() {
		return result;
	}

	public void setResult(List<MarketCatalogue> result) {
		this.result = result;
	}

}
