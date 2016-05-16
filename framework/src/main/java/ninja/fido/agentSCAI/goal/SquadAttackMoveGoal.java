/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.Position;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

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
