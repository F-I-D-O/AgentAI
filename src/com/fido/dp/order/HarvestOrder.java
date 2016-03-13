/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.HarvestGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestOrder extends GoalOrder{
	
	private final double mineralShare;

	public HarvestOrder(ResourceCommand target, CommandAgent commandAgent, double mineralShare) {
		super(target, commandAgent);
		this.mineralShare = mineralShare;
	}

	@Override
	protected void execute() {
		setGoal(new HarvestGoal(getTarget(), mineralShare));
	}
	
}
