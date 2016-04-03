/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.Position;
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

	private final Position expansionPosition;
	
	
	
	public UnitType getExpansionBuildingType() {
		return expansionBuildingType;
	}

	public Position getExpansionPosition() {
		return expansionPosition;
	}
	
	
	

	public StartExpansionGoal(Worker agent, GoalOrder order, UnitType expansionBuilding, Position expansionPosition) {
		super(agent, order);
		this.expansionPosition = expansionPosition;
		this.expansionBuildingType = expansionBuilding;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}
	
}
