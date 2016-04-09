/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.Scout;
import ninja.fido.agentai.activity.ConstructBuilding;
import ninja.fido.agentai.activity.ExploreBaseLocation;
import ninja.fido.agentai.activity.HarvestMinerals;
import ninja.fido.agentai.activity.Move;
import ninja.fido.agentai.activity.StartExpansion;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.decisionMaking.DecisionTable;
import ninja.fido.agentai.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.ConstructBuildingGoal;
import ninja.fido.agentai.goal.ExploreBaseLocationGoal;
import ninja.fido.agentai.goal.HarvestMineralsGoal;
import ninja.fido.agentai.goal.MoveGoal;
import ninja.fido.agentai.goal.StartExpansionGoal;
import ninja.fido.agentai.info.UnitCreationStartedInfo;
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
