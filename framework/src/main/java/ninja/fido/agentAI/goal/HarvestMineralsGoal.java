/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import ninja.fido.agentAI.agent.unit.Worker;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsGoal extends Goal{
	
	public HarvestMineralsGoal(Worker agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
