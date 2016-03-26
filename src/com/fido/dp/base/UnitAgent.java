package com.fido.dp.base;

import bwapi.Unit;
import com.fido.dp.Log;
import com.fido.dp.info.EnemyBuildingDiscovered;
import java.util.logging.Level;

public abstract class UnitAgent extends Agent {

    protected final Unit unit;

	
	
	
    public Unit getUnit() {
        return unit;
    }
	
	
	

    public UnitAgent(Unit unit) {
        this.unit = unit;
    }
	
	
	
	
	public boolean isIdle() {
        return unit.isIdle();
    }
	
	public boolean canSeeUnit(Unit otherUnit){
		if(!otherUnit.isVisible()){
			return false;
		}
		
		int distance = unit.getPosition().getApproxDistance(otherUnit.getPosition());
//		return unit.getType().sightRange() >= distance;
		return 400 >= distance;
	}

	public void onEnemyBuildingDiscoverd(Unit unit) {
		Log.log(this, Level.INFO, "{0}: enemy building discovered: {1}", this.getClass(), unit);
		new EnemyBuildingDiscovered(getCommandAgent(), this, unit).send();
	}
}
