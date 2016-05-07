/* 
 * AgentAI - Demo
 */
package ninja.fido.agentAI.demo.goal;

import ninja.fido.agentAI.agent.ZergCommander;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategyGoal extends Goal{

	public OutbreakStrategyGoal(ZergCommander agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
