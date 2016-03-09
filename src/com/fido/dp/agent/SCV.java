package com.fido.dp.agent;

import bwapi.TilePosition;
import com.fido.dp.base.LeafAgent;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Scout;
import com.fido.dp.action.Action;
import com.fido.dp.action.HarvestMineralsAction;
import com.fido.dp.action.ConstructBuilding;
import com.fido.dp.action.ExploreBaseLocation;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;
import java.util.logging.Level;

public class SCV extends LeafAgent implements Scout {
	
	private boolean constructingBuilding;
	
	private UnitType constructedBuildingType;

	
	
	public boolean isConstructingBuilding() {
		return constructingBuilding;
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
			constructingBuilding = true;
			constructedBuildingType = buildingType;
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: cannot build here! {1}", this.getClass(), placeToBuildOn);
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

//    public void commandConstuctBuilding(UnitType buildingType, TilePosition placeToBuildOn) {
//		setCommandedAction(new ConstructBuilding(this, buildingType, placeToBuildOn));
//    }
}
