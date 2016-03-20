/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.agent.Barracks;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.AutomaticProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProductionOrder extends GoalOrder{

	public AutomaticProductionOrder(Barracks target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new AutomaticProductionGoal(getTarget(), this));
	}
	
}
