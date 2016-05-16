/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import ninja.fido.agentSCAI.agent.ExplorationCommand;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
