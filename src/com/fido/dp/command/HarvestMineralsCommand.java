/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.command;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.SCV;
import com.fido.dp.base.GoalCommand;
import com.fido.dp.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsCommand extends GoalCommand{

	public HarvestMineralsCommand(SCV target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new HarvestMineralsGoal(getTarget()));
	}
	
}
