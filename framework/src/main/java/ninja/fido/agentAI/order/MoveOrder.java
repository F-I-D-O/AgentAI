/* 
 * AgentAI
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.MoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class MoveOrder extends GoalOrder<UnitAgent>{
	
	private final Position targetPosition;

	public MoveOrder(UnitAgent target, CommandAgent commandAgent, Position targetPosition)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.targetPosition = targetPosition;
	}

	@Override
	protected void execute() {
		setGoal(new MoveGoal(getTarget(), this, targetPosition));
	}
	
}
