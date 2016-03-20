/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.GameAPI;
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
		createConstructionFinishedEnvents();
	}
	
	public void addListener(EventEngineListener listener){
		listeners.add(listener);
	}
	

	private void createConstructionFinishedEnvents() {
		for (Unit building : GameAPI.getGame().self().getUnits()) {
			if(building.getType().isBuilding() && !building.isBeingConstructed() 
					&& !constructedBuildings.contains(building)){
				for (EventEngineListener listener : listeners) {
					listener.onBuildingConstructionFinished(building);
				}
				constructedBuildings.add(building);
			}
		}
	}

}
