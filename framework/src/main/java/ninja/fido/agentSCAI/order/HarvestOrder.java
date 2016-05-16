/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.agent.ResourceCommand;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.HarvestGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestOrder extends GoalOrder<ResourceCommand>{
	
	private final double mineralShare;

	public HarvestOrder(ResourceCommand target, CommandAgent commandAgent, double mineralShare) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.mineralShare = mineralShare;
	}

	@Override
	protected void execute() {
		setGoal(new HarvestGoal(getTarget(), this, mineralShare));
	}
	
}
