/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.AttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveOrder extends GoalOrder<UnitAgent>{
	
	private final Position attackTarget;

	public AttackMoveOrder(UnitAgent target, CommandAgent commandAgent, Position attackTarget)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new AttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
