/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.Position;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.GameAPI;
import java.util.Objects;
import ninja.fido.agentSCAI.goal.AttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMove extends Activity<UnitAgent,AttackMoveGoal,AttackMove>{
	
	private final Position target;
	
	int counter = 0;

	
	
	public AttackMove() {
		this.target = null;
	}
	

	public AttackMove(UnitAgent agent, AttackMoveGoal goal) {
		super(agent);
		this.target = goal.getAttackTarget();
	}
	
	public AttackMove(UnitAgent agent, Position target) {
		super(agent);
		this.target = target;
	}

	@Override
	protected void performAction() {
//		counter++;
//		if(agent.getUnit().canMove()){
//			boolean ok = agent.getUnit().move(new Position(0, 0));
//		}
//		boolean ok3 = agent.getUnit().canMove();
//		boolean ok = agent.getUnit().move(new Position(0, 0));
//		boolean ok2 = ok;
		
	}

	@Override
	protected void init() {
		GameAPI.attackMove(agent, target);
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
		final AttackMove other = (AttackMove) obj;
		if (!Objects.equals(this.target, other.target)) {
			return false;
		}
		return true;
	}

	@Override
	public AttackMove create(UnitAgent agent, AttackMoveGoal goal) {
		return new AttackMove(agent, goal);
	}
	
	
	
}
