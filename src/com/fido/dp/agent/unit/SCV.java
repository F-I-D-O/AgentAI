package com.fido.dp.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.Scout;
import com.fido.dp.base.Activity;
import com.fido.dp.activity.HarvestMinerals;
import com.fido.dp.activity.ConstructBuilding;
import com.fido.dp.activity.ExploreBaseLocation;
import com.fido.dp.activity.Move;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;
import com.fido.dp.goal.MoveGoal;
import com.fido.dp.info.UnitCreationStartedInfo;
import java.util.logging.Level;

public class SCV extends ArtificialWorker implements Scout {

    public SCV(Unit unit) {
        super(unit);
    }

	@Override
	public Position getPosition() {
		return unit.getPosition();
	}
	
	

    @Override
    protected Activity chooseAction() {
        if(getGoal() instanceof ExploreBaseLocationGoal){
			ExploreBaseLocationGoal goal = getGoal();
			return new ExploreBaseLocation(this, goal.getBaseLocation());
		}
		else if(getGoal() instanceof HarvestMineralsGoal){
			return new HarvestMinerals(this);
		}
		else if(getGoal() instanceof ConstructBuildingGoal){
			ConstructBuildingGoal goal = getGoal();
			return new ConstructBuilding(this, goal.getBuildingType(), goal.getPlaceToBuildOn());
		}
		else if(getGoal() instanceof MoveGoal){
			MoveGoal goal = getGoal();
			return new Move(this, goal.getTargetPosition());
		}
		return null;
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



	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}
}
