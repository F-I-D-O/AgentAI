/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.agent.UnitCommand;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.BBSAttackGoal;

/**
 *
 * @author F.I.D.O.
 */
public class BBSAttackOrder extends GoalOrder{

	public BBSAttackOrder(UnitCommand target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new BBSAttackGoal(getTarget(), this));
	}
	
}
