/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.agent.unit.Barracks;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.AutomaticProductionGoal;

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
