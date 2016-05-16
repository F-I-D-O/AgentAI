/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Unit;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.MorphableUnit;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.HarvestMineralsGoal;
import java.util.logging.Level;
import ninja.fido.agentSCAI.base.GameAgent;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Drone extends Worker<Drone> implements MorphableUnit{

	public Drone() throws EmptyDecisionTableMapException {
	}

	public Drone(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}

//	public void morph(UnitType buildingType) {
//		Log.log(this, Level.INFO, "{0}: morphing into: {1}", this.getClass(), buildingType.getClass());
//		
//		if(GameAPI.getGame().canBuildHere(unit.getTilePosition(), buildingType)){
//			unit.morph(buildingType);
//			constructionProcessInProgress = true;
//			constructedBuildingType = buildingType;
//		}
//		else{
//			Log.log(this, Level.SEVERE, "{0}: cannot build here! position: {1}, building: {2}", this.getClass(), 
//					unit.getTilePosition(), buildingType);
//		}
//		
//	}

	@Override
	public void onMorphFinished() {
		Log.log(this, Level.INFO, "{0}: morph into {1} finished.", this.getClass(), unit.getType());
	}

	@Override
	public Drone create(Unit unit) throws EmptyDecisionTableMapException {
		return new Drone(unit);
	}

	
	
}
