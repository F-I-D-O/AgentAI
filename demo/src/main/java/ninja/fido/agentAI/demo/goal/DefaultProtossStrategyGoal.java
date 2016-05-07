/* 
 * AgentAI - Demo
 */
package ninja.fido.agentAI.demo.goal;

import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultProtossStrategyGoal extends Goal {

	public DefaultProtossStrategyGoal(FullCommander agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
