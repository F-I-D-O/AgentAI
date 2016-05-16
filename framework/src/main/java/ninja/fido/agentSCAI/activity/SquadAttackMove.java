/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.Position;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.goal.SquadAttackMoveGoal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 * @param <AC> Activity
 */
public abstract class SquadAttackMove<AC extends SquadAttackMove>
extends CommandActivity<SquadCommander,SquadAttackMoveGoal,AC>{
	
	protected Position attackTarget;

	public SquadAttackMove() {
	}

	public SquadAttackMove(SquadCommander agent, SquadAttackMoveGoal goal) {
		super(agent);
		this.attackTarget = goal.getAttackTarget();
	}

	
	
	public SquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent);
		this.attackTarget = attackTarget;
	}

	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SquadAttackMove other = (SquadAttackMove) obj;
		if (!Objects.equals(this.attackTarget, other.attackTarget)) {
			return false;
		}
		return true;
	}

	
	
}
