package com.tom.autobetter.containers;

import com.tom.autobetter.entity.betfair.EventTypeResult;

import java.util.List;


public class EventTypeResultContainer extends Container {
	
	private List<EventTypeResult> result;
		
	public List<EventTypeResult> getResult() {
		return result;
	}
	public void setResult(List<EventTypeResult> result) {
		this.result = result;
	}
}
