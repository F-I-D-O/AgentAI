/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import com.fido.dp.base.UnitActivity;
import bwapi.UnitType;
import com.fido.dp.agent.Barracks;
import com.fido.dp.base.Goal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProduction extends UnitActivity<Barracks,Goal>{
	
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
		final AutomaticProduction other = (AutomaticProduction) obj;
		if (!Objects.equals(this.unitType, other.unitType)) {
			return false;
		}
		return true;
	}
	
}
