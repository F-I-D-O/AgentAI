/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.goal;

import bwapi.Position;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.GameAgent;

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
