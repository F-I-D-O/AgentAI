/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.order;

import ninja.fido.agentAI.agent.ProductionCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.demo.goal.BBSProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class BBSProductionOrder extends GoalOrder<ProductionCommand> {

	public BBSProductionOrder(ProductionCommand target, CommandAgent commandAgent) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new BBSProductionGoal(getTarget(), this));
	}
	
}
