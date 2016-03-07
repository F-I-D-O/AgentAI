/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.command;

import bwapi.Position;
import com.fido.dp.Scout;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Agent;
import com.fido.dp.base.GoalCommand;
import com.fido.dp.goal.ExploreBaseLocationGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ExploreBaseLocationCommand extends GoalCommand{
	
	private final Position baseLocation;

	public ExploreBaseLocationCommand(Scout target, CommandAgent commandAgent, Position baseLocation) {
		super((Agent) target, commandAgent);
		this.baseLocation = baseLocation;
	}

	@Override
	protected void execute() {
		setGoal(new ExploreBaseLocationGoal(getTarget(), baseLocation));
	}	
	
	
}
