/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import ninja.fido.agentAI.base.GameAPI;
import bwapi.Unit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class EventEngine {
	private final List<Unit> finishedBuildings;
	
	private final List<Unit> unfinishedBuildings;
	
	private final ArrayList<EventEngineListener> listeners;

	
	
	public EventEngine() {
		finishedBuildings = new ArrayList<>();
		unfinishedBuildings = new ArrayList<>();
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
					&& !finishedBuildings.contains(building)){
				for (EventEngineListener listener : listeners) {
					listener.onBuildingConstructionFinished(building);
				}
				finishedBuildings.add(building);
			}
		}
	}

	
}
