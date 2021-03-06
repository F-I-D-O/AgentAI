/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.order;

import ninja.fido.agentSCAI.agent.UnitCommand;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.demo.goal.BBSAttackGoal;

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
