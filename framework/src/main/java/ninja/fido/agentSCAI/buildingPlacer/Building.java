/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentSCAI.buildingPlacer;

import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

/**
 * Building from UAlbertBot
 * @author F.I.D.O.
 */
public class Building {
	
	private final Position desiredPosition;
	
	private final UnitType type;
	
	private final Unit builderUnit;
	
	private final boolean gasSteal;

	
	
	public Position getDesiredPosition() {
		return desiredPosition;
	}

	public UnitType getType() {
		return type;
	}

	public Unit getBuilderUnit() {
		return builderUnit;
	}

	public boolean isGasSteal() {
		return gasSteal;
	}

	
	
	public Building(Position desiredPosition, UnitType type, Unit builderUnit, boolean gasSteal) {
		this.desiredPosition = desiredPosition;
		this.type = type;
		this.builderUnit = builderUnit;
		this.gasSteal = gasSteal;
	}
	
	
	
	
}
