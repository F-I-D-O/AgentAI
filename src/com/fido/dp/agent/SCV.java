package com.fido.dp.agent;

import com.fido.dp.base.LeafAgent;
import bwapi.Unit;
import com.fido.dp.Scout;
import com.fido.dp.action.Action;
import com.fido.dp.action.HarvestMineralsAction;
import com.fido.dp.action.ConstructBuilding;
import com.fido.dp.action.ExploreBaseLocation;
import com.fido.dp.goal.ConstructBuildingGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.goal.HarvestMineralsGoal;

public class SCV extends LeafAgent implements Scout {

//    @Override
//    public void commandExploreBaseLocation(Position targetBase) {
//        setCommandedAction(new ExploreBaseLocation(this, targetBase));
//    }

    public SCV(Unit unit) {
        super(unit);
    }

//    public void commandHarvest(Material material) {
//        if (material == Material.MINERALS) {
//            setCommandedAction(new HarvestMineralsAction(this));
//        }
//    }

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
