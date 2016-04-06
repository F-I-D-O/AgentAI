package com.fido.dp.activity;

import com.fido.dp.base.UnitActivity;
import com.fido.dp.base.GameAPI;
import bwapi.Unit;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Goal;

public class HarvestMinerals<A extends Worker,G extends Goal> extends UnitActivity<A,G> {

    public HarvestMinerals(A unitAgent) {
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
        final HarvestMinerals other = (HarvestMinerals) obj;
        return true;
    }

	@Override
	public void initialize(A agent, G goal) {
		super.initialize(agent, goal);
	}
	
	

	@Override
	protected void init() {
		
	}

}
