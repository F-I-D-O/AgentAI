/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import bwapi.Position;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.goal.AttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveOrder extends GoalOrder{
	
	private final Position attackTarget;

	public AttackMoveOrder(GameAgent target, CommandAgent commandAgent, Position attackTarget) {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new AttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
