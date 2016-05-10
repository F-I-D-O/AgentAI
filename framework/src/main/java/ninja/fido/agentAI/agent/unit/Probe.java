/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.HarvestMinerals;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Probe extends Worker{

	public Probe(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}

	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(HarvestMineralsGoal.class, new HarvestMinerals());

		return defaultActivityMap;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}

	@Override
	public GameAgent create(Unit unit) throws EmptyDecisionTableMapException {
		return new Probe(unit);
	}
	
}
