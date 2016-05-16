/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.activity.Move;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class HighTemplar extends UnitAgent<HighTemplar>{

	public HighTemplar() throws EmptyDecisionTableMapException {
	}

	public HighTemplar(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());
		defaultActivityMap.put(MoveGoal.class, new Move());

		return defaultActivityMap;
	}
	
	@Override
	public HighTemplar create(Unit unit) throws EmptyDecisionTableMapException {
		return new HighTemplar(unit);
	}
	

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}

}
