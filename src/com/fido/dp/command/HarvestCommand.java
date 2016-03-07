/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.command;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.base.GoalCommand;
import com.fido.dp.goal.HarvestGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestCommand extends GoalCommand{
	
	private final double mineralShare;

	public HarvestCommand(ResourceCommand target, CommandAgent commandAgent, double mineralShare) {
		super(target, commandAgent);
		this.mineralShare = mineralShare;
	}

	@Override
	protected void execute() {
		setGoal(new HarvestGoal(getTarget(), mineralShare));
	}
	
}
