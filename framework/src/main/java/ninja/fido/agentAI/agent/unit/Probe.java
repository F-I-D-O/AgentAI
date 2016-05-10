/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.ConstructBuilding;
import ninja.fido.agentAI.activity.ExploreBaseLocation;
import ninja.fido.agentAI.activity.HarvestMinerals;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.ConstructBuildingGoal;
import ninja.fido.agentAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Probe extends Worker<Probe>{

	public Probe() throws EmptyDecisionTableMapException {
	}

	public Probe(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}

	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(HarvestMineralsGoal.class, new HarvestMinerals());		
		defaultActivityMap.put(ExploreBaseLocationGoal.class, new ExploreBaseLocation());
		defaultActivityMap.put(ConstructBuildingGoal.class, new ConstructBuilding());
		defaultActivityMap.put(MoveGoal.class, new Move());

		return defaultActivityMap;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}

	@Override
	public Probe create(Unit unit) throws EmptyDecisionTableMapException {
		return new Probe(unit);
	}
	
}
