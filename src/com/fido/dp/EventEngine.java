/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Unit;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class EventEngine {
	private final ArrayList<Unit> constructedBuildings;
	
	private final ArrayList<EventEngineListener> listeners;

	
	
	public EventEngine() {
		constructedBuildings = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	

	public void run(){
		createConstructionStartedEnvents();
//		createConstructionEndedEnvents();
	}
	
	public void addListener(EventEngineListener listener){
		listeners.add(listener);
	}
	

	private void createConstructionStartedEnvents() {
		for (Unit building : GameAPI.getGame().self().getUnits()) {
			if(building.getType().isBuilding() && building.isBeingConstructed() 
					&& !constructedBuildings.contains(building)){
				for (EventEngineListener listener : listeners) {
					listener.onBuildingConstructionStart(building);
				}
				constructedBuildings.add(building);
			}
		}
	}

//	private void createConstructionEndedEnvents() {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
}
