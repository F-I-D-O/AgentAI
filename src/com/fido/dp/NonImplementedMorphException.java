/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.UnitType;

/**
 *
 * @author F.I.D.O.
 */
public class NonImplementedMorphException extends Exception{
	
	private final MorphableUnit formerUnitAgent;
	
	private final UnitType unitType;

	
	
	public NonImplementedMorphException(MorphableUnit formerUnitAgent, UnitType unitType) {
		this.formerUnitAgent = formerUnitAgent;
		this.unitType = unitType;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return formerUnitAgent + ": morph to " + unitType + " is not implemented";
	}
}
