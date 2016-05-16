/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.goal;

import ninja.fido.agentAI.agent.ProductionCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSProductionGoal extends Goal{
	
	public BBSProductionGoal(ProductionCommand agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
