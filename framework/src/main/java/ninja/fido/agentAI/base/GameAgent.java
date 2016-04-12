package ninja.fido.agentAI.base;

import bwapi.Unit;
import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.info.EnemyBuildingDiscovered;
import java.util.logging.Level;

public abstract class GameAgent extends Agent {

    protected final Unit unit;

	
	
	
    public Unit getUnit() {
        return unit;
    }
	
	
	

    public GameAgent(Unit unit) {
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
