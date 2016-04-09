package ninja.fido.agentai.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentai.Scout;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.activity.HarvestMinerals;
import ninja.fido.agentai.activity.ConstructBuilding;
import ninja.fido.agentai.activity.ExploreBaseLocation;
import ninja.fido.agentai.activity.Move;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.ConstructBuildingGoal;
import ninja.fido.agentai.goal.ExploreBaseLocationGoal;
import ninja.fido.agentai.goal.HarvestMineralsGoal;
import ninja.fido.agentai.goal.MoveGoal;

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
