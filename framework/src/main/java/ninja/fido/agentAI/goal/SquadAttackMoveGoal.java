/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import bwapi.Position;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMoveGoal extends Goal{
	
	private final Position attackTarget;
	
	public SquadAttackMoveGoal(SquadCommander agent, GoalOrder order, Position attackTarget) {
		super(agent, order);
		this.attackTarget = attackTarget;
	}

	public Position getAttackTarget() {
		return attackTarget;
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
