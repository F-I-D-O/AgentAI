/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
