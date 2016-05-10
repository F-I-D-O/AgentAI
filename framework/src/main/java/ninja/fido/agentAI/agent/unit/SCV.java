/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.agent.Scout;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.activity.HarvestMinerals;
import ninja.fido.agentAI.activity.ConstructBuilding;
import ninja.fido.agentAI.activity.ExploreBaseLocation;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.ConstructBuildingGoal;
import ninja.fido.agentAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
