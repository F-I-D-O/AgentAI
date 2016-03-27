/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansionGoal extends Goal{
	
	private final UnitType expansionBuildingType;

	private final TilePosition buildigPosition;
	
	
	
	public UnitType getExpansionBuildingType() {
		return expansionBuildingType;
	}

	public TilePosition getBuildigPosition() {
		return buildigPosition;
	}
	
	
	

	public StartExpansionGoal(Worker agent, GoalOrder order, UnitType expansionBuilding, TilePosition buildigPosition) {
		super(agent, order);
		this.buildigPosition = buildigPosition;
		this.expansionBuildingType = expansionBuilding;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}
	
}
