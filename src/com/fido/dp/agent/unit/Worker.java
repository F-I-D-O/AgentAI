/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.Log;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.Scout;
import com.fido.dp.activity.ConstructBuilding;
import com.fido.dp.activity.ExploreBaseLocation;
import com.fido.dp.activity.HarvestMinerals;
import com.fido.dp.activity.Move;
import com.fido.dp.activity.StartExpansion;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;
import com.fido.dp.goal.MoveGoal;
import com.fido.dp.goal.StartExpansionGoal;
import com.fido.dp.info.UnitCreationStartedInfo;
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

	public void handleInvalidBuildPosition(TilePosition targetTilePosition, Unit unit) {
		invalidBuildPositionFailure = true;
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new HarvestMinerals(null));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(HarvestMineralsGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ExploreBaseLocation(null, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ExploreBaseLocationGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Move(null, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(MoveGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new StartExpansion(null, null, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(StartExpansionGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ConstructBuilding(null, null, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ConstructBuildingGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
	
	
}
