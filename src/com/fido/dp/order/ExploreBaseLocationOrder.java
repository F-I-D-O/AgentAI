/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import bwapi.Position;
import com.fido.dp.Scout;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Agent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.ExploreBaseLocationGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ExploreBaseLocationOrder extends GoalOrder{
	
	private final Position baseLocation;

	public ExploreBaseLocationOrder(Scout target, CommandAgent commandAgent, Position baseLocation) {
		super((Agent) target, commandAgent);
		this.baseLocation = baseLocation;
	}

	@Override
	protected void execute() {
		setGoal(new ExploreBaseLocationGoal(getTarget(), this, baseLocation));
	}	
	
	
}
