/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuilding extends LeafAction {
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuilding(Agent agent, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	public void performAction() {
		if(getUnit().isIdle()){
			getUnit().build(buildingType, placeToBuildOn);
		}
	}
	
}
