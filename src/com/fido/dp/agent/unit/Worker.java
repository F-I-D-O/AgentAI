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
import com.fido.dp.Material;
import com.fido.dp.Scout;
import com.fido.dp.activity.ExploreBaseLocation;
import com.fido.dp.activity.HarvestMinerals;
import com.fido.dp.activity.Move;
import com.fido.dp.activity.StartExpansion;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;
import com.fido.dp.goal.MoveGoal;
import com.fido.dp.goal.StartExpansionGoal;
import com.fido.dp.info.UnitCreationStartedInfo;
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
	
		
	
	public boolean isConstructingBuilding() {
		return constructionProcessInProgress;
	}
	
	
	
	public Worker(Unit unit) {
		super(unit);
		
		reasoningOn = true;
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new HarvestMinerals(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(HarvestMineralsGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ExploreBaseLocation(this, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ExploreBaseLocationGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Move(this, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(MoveGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new StartExpansion(this, null, null));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(StartExpansionGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}
	
	@Override
	public Position getPosition() {
		return unit.getPosition();
	}
	
	public void onConstructionStarted(){
		Log.log(this, Level.INFO, "{0}: onConstructionStarted", this.getClass());
		spendSupply(Material.GAS, constructedBuildingType.gasPrice());
		spendSupply(Material.MINERALS, constructedBuildingType.mineralPrice());
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
		if(placeToBuildOn == null){
			Log.log(this, Level.SEVERE, "{0}: place to build on is null!", this.getClass());
			return;
		}
		
		if(GameAPI.getGame().canBuildHere(placeToBuildOn, buildingType)){
			unit.build(buildingType, placeToBuildOn);
			constructionProcessInProgress = true;
			constructedBuildingType = buildingType;
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: cannot build here! position: {1}, building: {2}", this.getClass(), 
					placeToBuildOn, buildingType);
		}
	}
	
}
