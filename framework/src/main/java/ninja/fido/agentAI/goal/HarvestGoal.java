/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import ninja.fido.agentAI.agent.ResourceCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestGoal extends Goal{
	
	private final double mineralShare;

	public double getMineralShare() {
		return mineralShare;
	}
	
	
	
	public HarvestGoal(ResourceCommand agent, GoalOrder order, double mineralShare) {
		super(agent, order);
		this.mineralShare = mineralShare;
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
