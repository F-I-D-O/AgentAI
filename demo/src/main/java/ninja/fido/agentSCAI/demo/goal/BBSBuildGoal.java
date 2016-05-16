/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentSCAI.agent.BuildCommand;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
