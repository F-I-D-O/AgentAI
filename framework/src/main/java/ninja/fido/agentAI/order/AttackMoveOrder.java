/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import bwapi.Position;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.AttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveOrder extends GoalOrder{
	
	private final Position attackTarget;

	public AttackMoveOrder(GameAgent target, CommandAgent commandAgent, Position attackTarget)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new AttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
