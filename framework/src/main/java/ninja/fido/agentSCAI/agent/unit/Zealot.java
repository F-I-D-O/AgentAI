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
public class Zealot extends UnitAgent{

	
	public Zealot() throws EmptyDecisionTableMapException {
	}
	
	public Zealot(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}

	

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}

	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
	}

	@Override
	public GameAgent create(Unit unit) throws EmptyDecisionTableMapException {
		return new Zealot(unit);
	}
	
	
	
}
