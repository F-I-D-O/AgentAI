/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.GameAPI;
import bwapi.Unit;
import com.fido.dp.agent.LeafAgent;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsAction extends UnitAction{

	public HarvestMineralsAction(LeafAgent unitAgent) {
		super(unitAgent);
	}

	@Override
	public void performAction() {
		if(getUnitAgent().isIdle()){
			Unit closestMineral = null;
			Unit unit = getUnitAgent().getUnit();

			//find the closest mineral
			for (Unit neutralUnit : GameAPI.getGame().neutral().getUnits()) {
				if (neutralUnit.getType().isMineralField()) {
					if (closestMineral == null || unit.getDistance(neutralUnit) < unit.getDistance(closestMineral)) {
						closestMineral = neutralUnit;
					}
				}
			}

			//if a mineral patch was found, send the drone to gather it
			if (closestMineral != null) {
				unit.gather(closestMineral, false);
			}
		}
	}
	
}
