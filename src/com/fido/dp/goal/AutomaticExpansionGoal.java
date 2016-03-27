/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.UnitType;
import com.fido.dp.agent.ExpansionCommand;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticExpansionGoal extends Goal{
	
	private final UnitType expansionBuildingType;

	
	
	public UnitType getExpansionBuildingType() {
		return expansionBuildingType;
	}
	
	
	
	

	public AutomaticExpansionGoal(ExpansionCommand agent, GoalOrder order, UnitType expansionBuildingType) {
		super(agent, order);
		this.expansionBuildingType = expansionBuildingType;
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
