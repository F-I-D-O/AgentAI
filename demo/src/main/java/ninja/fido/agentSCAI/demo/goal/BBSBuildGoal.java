/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentAI.agent.BuildCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSBuildGoal extends Goal{
	
	public BBSBuildGoal(BuildCommand agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
