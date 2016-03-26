/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.ConstructBuildingGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingOrder extends GoalOrder{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuildingOrder(SCV target, CommandAgent commandAgent, UnitType buildingType, 
			TilePosition placeToBuildOn) {
		super(target, commandAgent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	protected void execute() {
		setGoal(new ConstructBuildingGoal(getTarget(), this, buildingType, placeToBuildOn));
	}
	
}
