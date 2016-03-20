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
import com.fido.dp.goal.MoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class MoveOrder extends GoalOrder{
	
	private final Position targetPosition;

	public MoveOrder(UnitAgent target, CommandAgent commandAgent, Position targetPosition) {
		super(target, commandAgent);
		this.targetPosition = targetPosition;
	}

	@Override
	protected void execute() {
		setGoal(new MoveGoal(getTarget(), this, targetPosition));
	}
	
}
