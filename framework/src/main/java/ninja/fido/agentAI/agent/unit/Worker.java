/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.agent.Scout;
import ninja.fido.agentAI.activity.ConstructBuilding;
import ninja.fido.agentAI.activity.ExploreBaseLocation;
import ninja.fido.agentAI.activity.HarvestMinerals;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.activity.StartExpansion;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.ConstructBuildingGoal;
import ninja.fido.agentAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.goal.StartExpansionGoal;
import ninja.fido.agentAI.info.UnitCreationStartedInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Worker extends UnitAgent implements Scout {
	
	private boolean constructionInProgress;
	
	protected boolean constructionProcessInProgress;
	
	protected UnitType constructedBuildingType;
	
	protected boolean invalidBuildPositionFailure;
		
	
	public boolean isConstructingBuilding() {
		return constructionProcessInProgress;
	}

	public boolean isInvalidBuildPositionFailure() {
		return invalidBuildPositionFailure;
	}

	public Worker() {
	}
	
	
	public Worker(Unit unit) {
		super(unit);
	}
	
	@Override
	public Position getPosition() {
		return unit.getPosition();
	}
	
	public void onConstructionStarted() throws ResourceDeficiencyException{
		Log.log(this, Level.INFO, "{0}: onConstructionStarted", this.getClass());
		spendSupply(ResourceType.GAS, constructedBuildingType.gasPrice());
		spendSupply(ResourceType.MINERALS, constructedBuildingType.mineralPrice());
//		spendSupply(ResourceType.SUPPLY, constructedBuildingType.supplyRequired());
		constructionInProgress = true;
		new UnitCreationStartedInfo(getCommandAgent(), this, constructedBuildingType).send();
	}
	
	public void onConstructionFinished() {
		Log.log(this, Level.INFO, "{0}: onConstructionFinished", this.getClass());
		constructionInProgress = false;
		constructionProcessInProgress = false;
		constructedBuildingType = null;
		if(getGoal() instanceof ConstructBuildingGoal){
			((ConstructBuildingGoal) getGoal()).setBuildingConstructionFinished(true);
		}
	}
	
	public boolean haveEnoughResourcersToBuild(UnitType buildingType){
		return buildingType.mineralPrice() <= getOwnedMinerals() && buildingType.gasPrice() <= getOwnedGas();
	}
	
	public int getMissingMinerals(UnitType buildingType){
		int difference = buildingType.mineralPrice() - getOwnedMinerals();
		return difference > 0 ? difference : 0;
	}
	
	public int getMissingGas(UnitType buildingType){
		int difference = buildingType.gasPrice() - getOwnedGas();
		return difference > 0 ? difference : 0;
	}
	
	public void build(UnitType buildingType, TilePosition placeToBuildOn){
		invalidBuildPositionFailure = false;
		GameAPI.build(this, buildingType, placeToBuildOn);
		constructionProcessInProgress = true;
		constructedBuildingType = buildingType;
	}

	public void handleInvalidBuildPosition(TilePosition targetTilePosition) {
		invalidBuildPositionFailure = true;
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new HarvestMinerals());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(HarvestMineralsGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ExploreBaseLocation());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ExploreBaseLocationGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Move());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(MoveGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new StartExpansion());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(StartExpansionGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ConstructBuilding());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ConstructBuildingGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
	
	
}
