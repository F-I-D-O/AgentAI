/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
