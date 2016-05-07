/* 
 * AgentAI - Demo
 */
package ninja.fido.agentAI.demo.order;

import ninja.fido.agentAI.agent.UnitCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.demo.goal.BBSAttackGoal;

/**
 *
 * @author F.I.D.O.
 */
public class BBSAttackOrder extends GoalOrder{

	public BBSAttackOrder(UnitCommand target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new BBSAttackGoal(getTarget(), this));
	}
	
}
