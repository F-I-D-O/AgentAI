/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import ninja.fido.agentAI.agent.ExplorationCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class StrategicExplorationGoal extends Goal{
	
	public StrategicExplorationGoal(ExplorationCommand agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
