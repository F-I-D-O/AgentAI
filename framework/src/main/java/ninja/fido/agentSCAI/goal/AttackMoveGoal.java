/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.Position;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveGoal extends Goal{
	
	private final Position attackTarget;
	
	public AttackMoveGoal(GameAgent agent, GoalOrder order, Position attackTarget) {
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
