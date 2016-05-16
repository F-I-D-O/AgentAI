/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.MorphableUnit;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GameAgent;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentSCAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentSCAI.goal.WaitGoal;
import java.util.TreeMap;
import java.util.logging.Level;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Larva extends GameAgent<Larva> implements MorphableUnit{

	@Override
	public void onMorphFinished() {
		Log.log(this, Level.INFO, "{0}: morph into {1} finished.", this.getClass(), unit.getType());
	}

	@Override
	public Larva create(Unit unit) throws EmptyDecisionTableMapException {
		return new Larva(unit);
	}
	
	public enum MorphOption {
		DRONE(UnitType.Zerg_Drone),
		OVERLORD(UnitType.Zerg_Overlord);
		
		private final UnitType unitType;
		
		MorphOption(UnitType unitType){
			this.unitType = unitType;
		}
		
//		public int getRequiredSupply(){
//			if(unitType.equals(UnitType.Zerg_Drone)){
//				return 1;
//			}
//			return unitType.supplyRequired();
//		}
	}

	public Larva() throws EmptyDecisionTableMapException {
	}

	public Larva(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	public void morph(MorphOption morphOption) throws ResourceDeficiencyException{
		Log.log(this, Level.INFO, "{0}: morphing into: {1}", this.getClass(), morphOption.unitType);
		spendResource(ResourceType.MINERALS, morphOption.unitType.mineralPrice());
		spendResource(ResourceType.GAS, morphOption.unitType.gasPrice());
		spendResource(ResourceType.SUPPLY, morphOption.unitType.supplyRequired());
		unit.morph(morphOption.unitType);
	}
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
	}


	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		decisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return decisionTablesMap;
	}
}
