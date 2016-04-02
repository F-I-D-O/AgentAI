/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.Building;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.activity.AutomaticExpansion;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Activity;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.AutomaticExpansionGoal;
import com.fido.dp.info.ExpansionInfo;
import com.fido.dp.base.Info;
import com.fido.dp.request.ResourceRequest;
import com.fido.dp.base.Request;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionCommand extends CommandAgent
{
	
	private final Queue<Worker> freeWorkers;
	
	private final BuildingPlacer buildingPlacer;
	
	private Position nextExpansionPosition;
	
	
	
	
	public Position getNextExpansionPosition() {
		return nextExpansionPosition;
	}

	public ExpansionCommand() {
		freeWorkers = new ArrayDeque<>();
		buildingPlacer = GameAPI.getBuildingPlacer();
		
		reasoningOn = true;
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new AutomaticExpansion(this, null));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(AutomaticExpansionGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}
	
	public int getNumberOfFreeWorkers(){
		return freeWorkers.size();
	}
	
	public Worker getWorker(){
		Worker worker = freeWorkers.poll();
		if(worker != null){
			worker.setAssigned(false);
		}
		return worker;
	}

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new AutomaticExpansionGoal(this, null, UnitType.Zerg_Hatchery);
	}
	
	@Override
	protected void routine() {
		super.routine(); 
		for (Worker worker : getCommandedAgents(Worker.class)) {
			if(!worker.IsAssigned()){
				addWorker(worker);
				worker.setAssigned(true);
			}
		}
	}
	
	private void addWorker(Worker worker){
		freeWorkers.add(worker);
//		if(numberOfMissingWorkers > 0){
//			numberOfMissingWorkers--;
//		}
	}
	
	public TilePosition findPositionForBuild(UnitType buildingType, Worker worker) {
		return buildingPlacer.getBuildingLocation(
				new Building(nextExpansionPosition, buildingType, worker.getUnit(), false));
	}

	@Override
	protected void processInfo(Info info) {
		if(info instanceof ExpansionInfo){
			nextExpansionPosition = ((ExpansionInfo) info).getExpansionPosition();
		}
	}

	@Override
	protected void handleRequest(Request request) {
		if(request instanceof ResourceRequest){
			ResourceRequest materialRequest = (ResourceRequest) request;
			if(materialRequest.getMineralAmount() <= getOwnedMinerals() 
					&& materialRequest.getGasAmount() <= getOwnedGas()){
				try {
					giveResource(materialRequest.getSender(), ResourceType.MINERALS, materialRequest.getMineralAmount());
					giveResource(materialRequest.getSender(), ResourceType.GAS, materialRequest.getGasAmount());
				} catch (ResourceDeficiencyException ex) {
					ex.printStackTrace();
				}
			}
			else {
				if(!materialRequest.isProcessed()){
					int missingMinerals = getMissingMinerals(materialRequest.getMineralAmount());
					int missingGas = getMissingGas( materialRequest.getGasAmount());
					new ResourceRequest(getCommandAgent(), this, missingMinerals, missingGas, 0).send();
				}
				materialRequest.setProcessed(true);
				queRequest(materialRequest);
			}
		}
	}
	
	
}
