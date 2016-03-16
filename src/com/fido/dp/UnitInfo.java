/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Position;
import bwapi.Unit;

/**
 *
 * @author F.I.D.O.
 */
public class UnitInfo {
	private Unit unit;
	
	private Position position;

	public Position getPosition() {
		return position;
	}
	
	
	
	

	public UnitInfo(Unit unit, Position position) {
		this.unit = unit;
		this.position = position;
	}
	
	
}
