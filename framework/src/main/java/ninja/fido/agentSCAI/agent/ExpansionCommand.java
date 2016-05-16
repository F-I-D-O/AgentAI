/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.agent;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.buildingPlacer.Building;
import ninja.fido.agentAI.buildingPlacer.BuildingPlacer;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.activity.AutomaticExpansion;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentSCAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentSCAI.goal.AutomaticExpansionGoal;
import ninja.fido.agentSCAI.info.ExpansionInfo;
import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.request.ResourceRequest;
import ninja.fido.agentSCAI.base.Request;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.order.StartExpansionOrder;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

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

	public ExpansionCommand() throws EmptyDecisionTableMapException {
		freeWorkers = new ArrayDeque<>();
		buildingPlacer = GameAPI.getBuildingPlacer();
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
				materialRequest.send();
			}
		}
	}

	public void startExpansion(UnitType expansionBuildingType, Worker worker) throws ChainOfCommandViolationException {
//		TilePosition buildingPosition = findPositionForBuild(expansionBuildingType, worker);
		new StartExpansionOrder(worker, this, expansionBuildingType, nextExpansionPosition).issueOrder();
		nextExpansionPosition = null;
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new AutomaticExpansion());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(AutomaticExpansionGoal.class));
		decisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return decisionTablesMap;
	}
	
	
}
