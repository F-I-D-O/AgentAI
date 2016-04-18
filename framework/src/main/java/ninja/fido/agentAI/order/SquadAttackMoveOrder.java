/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.SquadAttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMoveOrder extends GoalOrder<SquadCommander>{
	
	private final Position attackTarget;

	public SquadAttackMoveOrder(SquadCommander target, CommandAgent commandAgent, Position attackTarget)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new SquadAttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
