/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.command;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.SCV;
import com.fido.dp.base.GoalCommand;
import com.fido.dp.goal.ConstructBuildingGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingCommand extends GoalCommand{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuildingCommand(SCV target, CommandAgent commandAgent, UnitType buildingType, 
			TilePosition placeToBuildOn) {
		super(target, commandAgent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	protected void execute() {
		setGoal(new ConstructBuildingGoal(getTarget(), buildingType, placeToBuildOn));
	}
	
}
