/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import ninja.fido.agentAI.agent.LarvaCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

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
