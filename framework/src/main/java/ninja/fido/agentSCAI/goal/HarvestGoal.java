/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.goal;

import ninja.fido.agentSCAI.agent.ResourceCommand;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
