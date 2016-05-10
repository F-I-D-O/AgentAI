/*
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class CommandCenter extends GameAgent<CommandCenter>{

	public CommandCenter() throws EmptyDecisionTableMapException {
	}

	public CommandCenter(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();
		
		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
	}
	

	@Override
	public CommandCenter create(Unit unit) throws EmptyDecisionTableMapException {
		return new CommandCenter(unit);
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
