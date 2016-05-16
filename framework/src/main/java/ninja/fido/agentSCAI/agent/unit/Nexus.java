/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.GameAgent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Nexus extends GameAgent<Nexus>{
	
	public Nexus() throws EmptyDecisionTableMapException {
	}

	public Nexus(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();
		
		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
	}
	

	@Override
	public Nexus create(Unit unit) throws EmptyDecisionTableMapException {
		return new Nexus(unit);
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
}
