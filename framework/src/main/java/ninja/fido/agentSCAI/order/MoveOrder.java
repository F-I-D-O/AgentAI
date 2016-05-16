/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.MoveGoal;

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
