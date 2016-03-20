/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import bwapi.Position;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.goal.AttackMoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveOrder extends GoalOrder{
	
	private final Position attackTarget;

	public AttackMoveOrder(UnitAgent target, CommandAgent commandAgent, Position attackTarget) {
		super(target, commandAgent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void execute() {
		setGoal(new AttackMoveGoal(getTarget(), this, attackTarget));
	}
	
}
