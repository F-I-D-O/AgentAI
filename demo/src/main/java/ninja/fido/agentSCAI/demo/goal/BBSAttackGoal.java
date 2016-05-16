/* 
 * AgentAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSAttackGoal extends Goal{
	
	public BBSAttackGoal(Agent agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
