/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity;

import ninja.fido.agentai.base.UnitActivity;
import bwapi.UnitType;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.agent.unit.Barracks;
import ninja.fido.agentai.base.Goal;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		try {
			agent.automaticProduction();
		} catch (ResourceDeficiencyException ex) {
			ex.printStackTrace();
		}
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
