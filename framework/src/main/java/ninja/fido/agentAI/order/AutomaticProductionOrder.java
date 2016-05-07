/* 
 * AgentAI
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.agent.unit.Barracks;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.AutomaticProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProductionOrder extends GoalOrder<Barracks>{

	public AutomaticProductionOrder(Barracks target, CommandAgent commandAgent)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new AutomaticProductionGoal(getTarget(), this));
	}
	
}
