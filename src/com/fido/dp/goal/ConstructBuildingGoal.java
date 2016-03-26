/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingGoal extends Goal {
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;
	
	private boolean buildingConstructionFinished;

	
	
	public UnitType getBuildingType() {
		return buildingType;
	}

	public TilePosition getPlaceToBuildOn() {
		return placeToBuildOn;
	}

	public void setBuildingConstructionFinished(boolean buildingConstructionFinished) {
		this.buildingConstructionFinished = buildingConstructionFinished;
	}

	
	
	
	public ConstructBuildingGoal(SCV agent, GoalOrder order, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent, order);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	public boolean isCompleted() {
		return buildingConstructionFinished;
	}
	
}
