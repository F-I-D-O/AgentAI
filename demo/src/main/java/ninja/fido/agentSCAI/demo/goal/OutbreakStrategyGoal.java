/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentSCAI.agent.ZergCommander;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
