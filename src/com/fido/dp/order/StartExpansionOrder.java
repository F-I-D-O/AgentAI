/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.goal.StartExpansionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansionOrder extends GoalOrder{
	
	private final UnitType expansionBuildingType;

	private final TilePosition buildigPosition;

	public StartExpansionOrder(Worker target, CommandAgent commandAgent, UnitType expansionBuilding, 
			TilePosition buildigPosition) {
		super(target, commandAgent);
		this.buildigPosition = buildigPosition;
		this.expansionBuildingType = expansionBuilding;
	}

	@Override
	protected void execute() {
		setGoal(new StartExpansionGoal(getTarget(), this, expansionBuildingType, buildigPosition));
	}
	
}
