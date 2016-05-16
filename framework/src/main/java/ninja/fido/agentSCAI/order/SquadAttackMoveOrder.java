/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.SquadAttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMoveOrder extends GoalOrder<SquadCommander>{
	
	private final Position attackTarget;

	public SquadAttackMoveOrder(SquadCommander target, CommandAgent commandAgent, Position attackTarget)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new SquadAttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
