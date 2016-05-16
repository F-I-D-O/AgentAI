/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentAI.base.Commander;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSStrategyGoal extends Goal{
	
	public BBSStrategyGoal(Commander agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
