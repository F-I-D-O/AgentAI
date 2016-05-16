/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.activity.ConstructBuilding;
import ninja.fido.agentSCAI.activity.ExploreBaseLocation;
import ninja.fido.agentSCAI.activity.HarvestMinerals;
import ninja.fido.agentSCAI.activity.Move;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.ConstructBuildingGoal;
import ninja.fido.agentSCAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentSCAI.goal.HarvestMineralsGoal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
