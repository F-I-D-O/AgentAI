/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentai.Building;
import ninja.fido.agentai.BuildingPlacer;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.activity.AutomaticExpansion;
import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.modules.decisionMaking.DecisionTable;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.modules.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.AutomaticExpansionGoal;
import ninja.fido.agentai.info.ExpansionInfo;
import ninja.fido.agentai.base.Info;
import ninja.fido.agentai.request.ResourceRequest;
import ninja.fido.agentai.base.Request;
import ninja.fido.agentai.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.order.StartExpansionOrder;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.TreeMap;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;

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
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new AutomaticExpansion());
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
//		if(worker != null){
//			worker.setAssigned(false);
//		}
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

	public void startExpansion(UnitType expansionBuildingType, Worker worker) throws ChainOfCommandViolationException {
//		TilePosition buildingPosition = findPositionForBuild(expansionBuildingType, worker);
		new StartExpansionOrder(worker, this, expansionBuildingType, nextExpansionPosition).issueOrder();
		nextExpansionPosition = null;
	}
	
	
}
