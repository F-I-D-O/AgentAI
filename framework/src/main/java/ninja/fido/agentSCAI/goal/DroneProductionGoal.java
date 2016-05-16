/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import ninja.fido.agentSCAI.agent.LarvaCommand;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class DroneProductionGoal extends Goal{

	public DroneProductionGoal(LarvaCommand agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
