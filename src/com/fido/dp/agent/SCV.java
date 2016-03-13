package com.fido.dp.agent;

import bwapi.TilePosition;
import com.fido.dp.base.UnitAgent;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.Scout;
import com.fido.dp.base.Action;
import com.fido.dp.action.HarvestMineralsAction;
import com.fido.dp.action.ConstructBuilding;
import com.fido.dp.action.ExploreBaseLocation;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;
import com.fido.dp.request.UnitCreationStartedInfo;
import java.util.logging.Level;

public class SCV extends UnitAgent implements Scout {
	
	private boolean constructionProcessInProgress;
	
	private UnitType constructedBuildingType;
	private boolean constructionInProgress;

	
	
	public boolean isConstructingBuilding() {
		return constructionProcessInProgress;
	}

	
	


    public SCV(Unit unit) {
        super(unit);
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

    @Override
    protected Action chooseAction() {
        if(getGoal() instanceof ExploreBaseLocationGoal){
			ExploreBaseLocationGoal goal = getGoal();
			return new ExploreBaseLocation(this, goal.getBaseLocation());
		}
		else if(getGoal() instanceof HarvestMineralsGoal){
			return new HarvestMineralsAction(this);
		}
		else if(getGoal() instanceof ConstructBuildingGoal){
			ConstructBuildingGoal goal = getGoal();
			return new ConstructBuilding(this, goal.getBuildingType(), goal.getPlaceToBuildOn());
		}
		return null;
    }
	
	public void onConstructionStarted(){
		Log.log(this, Level.INFO, "{0}: onConstructionStarted", this.getClass());
		spendSupply(Material.GAS, constructedBuildingType.gasPrice());
		spendSupply(Material.MINERALS, constructedBuildingType.mineralPrice());
		constructionInProgress = true;
		new UnitCreationStartedInfo(geCommandAgent(), this, constructedBuildingType).send();
	}

//    public void commandConstuctBuilding(UnitType buildingType, TilePosition placeToBuildOn) {
//		setCommandedAction(new ConstructBuilding(this, buildingType, placeToBuildOn));
//    }

	@Override
	protected void routine() {
//		if(!constructionInProgress){
//			if(unit.isConstructing()){
//				onConstructionStarted();
//				
//			}
//		}
//		else{
//			if(unit.isConstructing()){
//				onConstructionFinished();
//			}
//		}
	}

	public void onConstructionFinished() {
		Log.log(this, Level.INFO, "{0}: onConstructionFinished", this.getClass());
		constructionInProgress = false;
		constructionProcessInProgress = false;
		constructedBuildingType = null;
	}
}
