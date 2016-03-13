/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.UnitType;
import com.fido.dp.agent.Barracks;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProduction extends UnitAction<Barracks>{
	
	UnitType unitType;

	public AutomaticProduction(Barracks unitAgent, UnitType unitType) {
		super(unitAgent);
		this.unitType = unitType;
	}

	@Override
	protected void performAction() {
		agent.automaticProduction();
	}

	@Override
	protected void init() {
		agent.setAutomaticProductionUnitType(unitType);
	}
	
}
