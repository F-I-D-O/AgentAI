package com.fido.dp.action;

import com.fido.dp.base.GameAPI;
import bwapi.Unit;
import com.fido.dp.agent.SCV;
import com.fido.dp.base.Goal;

public class HarvestMineralsAction extends UnitAction<SCV,Goal> {

    public HarvestMineralsAction(SCV unitAgent) {
        super(unitAgent);
    }

    @Override
    public void performAction() {
        if (agent.getUnit().isIdle()) {
            Unit closestMineral = null;
            Unit unit = agent.getUnit();
            for (Unit neutralUnit : GameAPI.getGame().neutral().getUnits()) {
                if (neutralUnit.getType().isMineralField()) {
                    if (closestMineral == null || unit.getDistance(neutralUnit) < unit.getDistance(closestMineral)) {
                        closestMineral = neutralUnit;
                    }
                }
            }
            if (closestMineral != null) {
                unit.gather(closestMineral, false);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HarvestMineralsAction other = (HarvestMineralsAction) obj;
        return true;
    }

	@Override
	protected void init() {
		
	}

}
