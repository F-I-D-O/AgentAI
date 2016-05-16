/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import ninja.fido.agentSCAI.agent.unit.Barracks;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProductionGoal extends Goal{
	
	public AutomaticProductionGoal(Barracks agent,  GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
