/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Unit;

/**
 *
 * @author F.I.D.O.
 */
public abstract class LeafAgent extends Agent {
	
	protected Unit unit;

	public boolean isIdle(){
		return unit.isIdle();
	}

	public Unit getUnit() {
		return unit;
	}

	public LeafAgent(Unit unit) {
		this.unit = unit;
	}
	
	
}
