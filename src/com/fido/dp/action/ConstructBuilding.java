/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.SCV;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuilding extends LeafAction<SCV>{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuilding(SCV agent, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConstructBuilding other = (ConstructBuilding) obj;
		if (!Objects.equals(this.buildingType, other.buildingType)) {
			return false;
		}
		if (!Objects.equals(this.placeToBuildOn, other.placeToBuildOn)) {
			return false;
		}
		return true;
	}
	
	
	
	

	@Override
	protected void init() {
		agent.build(buildingType, placeToBuildOn);
	}

	@Override
	protected void performAction() {
		
	}
	
	
	
}
