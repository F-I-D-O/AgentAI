/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import bwapi.Position;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.SquadAttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMoveOrder extends GoalOrder{
	
	private final Position attackTarget;

	public SquadAttackMoveOrder(SquadCommander target, CommandAgent commandAgent, Position attackTarget) {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new SquadAttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
