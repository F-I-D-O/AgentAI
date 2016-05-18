/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base;

import bwapi.Unit;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.info.EnemyBuildingDiscovered;
import java.util.logging.Level;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 * Game agent. Game agent is an agent that represents some unit in game.
 * @author david
 * @param <A> Agent type
 */
public abstract class GameAgent<A extends GameAgent> extends Agent {

	/**
	 * Unit that is represented by tthis agent.
	 */
    protected final Unit unit;

	
	
	
	/**
	 * Returns the unit that is represented by this agent.
	 * @return Returns the unit that is represented by this agent.
	 */
    public Unit getUnit() {
        return unit;
    }

	
	
	
	/**
	 * Empty constructor. This constructor is intended to be used in goal activity maps.
	 * @throws EmptyDecisionTableMapException If the decision tables map is not initialized.
	 */
	public GameAgent() throws EmptyDecisionTableMapException{
		unit = null;
	}

	/**
	 * Constructor. This constructor should by called by GameAPI when unit first appear in game.
	 * @param unit The unit that is represented by this agent.
	 * @throws EmptyDecisionTableMapException If the decision tables map is not initialized.
	 */
    public GameAgent(Unit unit) throws EmptyDecisionTableMapException {
        this.unit = unit;
    }
	
	
	
	/**
	 * Create new instance from empty agent.
	 * @param unit BWAPI unit.
	 * @return Returns new instance of the agent.
	 * @throws EmptyDecisionTableMapException If the decision tables map is not initialized.
	 */
	public abstract A create(Unit unit) throws EmptyDecisionTableMapException;
	
	/**
	 * Returns true if unit is idle (same as BWAPI isIdle). 
	 * @return Returns true if unit is idle (same as BWAPI isIdle).
	 */
	public final boolean isIdle() {
        return unit.isIdle();
    }
	
	
	/**
	 * Returns true if unit can see other unit.
	 * @param otherUnit Unit.
	 * @return Returns true if unit can see other unit.
	 */
	protected final boolean canSeeUnit(Unit otherUnit){
		if(!otherUnit.isVisible()){
			return false;
		}
		int distance = unit.getPosition().getApproxDistance(otherUnit.getPosition());
//		return unit.getType().sightRange() >= distance;
		return 400 >= distance;
	}

	
	/**
	 * Called by gameAPI when enemy unit is discoverd by this agent.
	 * @param unit Enemy unit.
	 */
	final void onEnemyBuildingDiscoverd(Unit unit) {
		Log.log(this, Level.INFO, "{0}: enemy building discovered: {1}", this.getClass(), unit);
		new EnemyBuildingDiscovered(getCommandAgent(), this, unit).send();
	}
}
