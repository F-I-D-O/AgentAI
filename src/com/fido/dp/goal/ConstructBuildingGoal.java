/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.SCV;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingGoal extends Goal {
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	
	
	public UnitType getBuildingType() {
		return buildingType;
	}

	public TilePosition getPlaceToBuildOn() {
		return placeToBuildOn;
	}

	
	
	public ConstructBuildingGoal(SCV agent, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}
	
}
