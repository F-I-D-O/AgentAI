/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.agent.Scout;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.activity.HarvestMinerals;
import ninja.fido.agentSCAI.activity.ConstructBuilding;
import ninja.fido.agentSCAI.activity.ExploreBaseLocation;
import ninja.fido.agentSCAI.activity.Move;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.base.GameAgent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.ConstructBuildingGoal;
import ninja.fido.agentSCAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentSCAI.goal.HarvestMineralsGoal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

public class SCV extends ArtificialWorker<SCV> implements Scout {

	public SCV() throws EmptyDecisionTableMapException {
	}

	public SCV(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}

	


	@Override
	public Position getPosition() {
		return unit.getPosition();
	}
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(ExploreBaseLocationGoal.class, new ExploreBaseLocation());
		defaultActivityMap.put(HarvestMineralsGoal.class, new HarvestMinerals());
		defaultActivityMap.put(ConstructBuildingGoal.class, new ConstructBuilding());
		defaultActivityMap.put(MoveGoal.class, new Move());
		
		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
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

	@Override
	public SCV create(Unit unit) throws EmptyDecisionTableMapException {
		return new SCV(unit);
	}
}
