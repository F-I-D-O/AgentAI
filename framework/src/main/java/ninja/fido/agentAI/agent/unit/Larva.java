/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.MorphableUnit;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.WaitGoal;
import java.util.TreeMap;
import java.util.logging.Level;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Larva extends GameAgent implements MorphableUnit{

	@Override
	public void onMorphFinished() {
		Log.log(this, Level.INFO, "{0}: morph into {1} finished.", this.getClass(), unit.getType());
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
		spendSupply(ResourceType.MINERALS, morphOption.unitType.mineralPrice());
		spendSupply(ResourceType.GAS, morphOption.unitType.gasPrice());
		spendSupply(ResourceType.SUPPLY, morphOption.unitType.supplyRequired());
		unit.morph(morphOption.unitType);
	}
	

	@Override
	protected Activity chooseActivity() {
		return null;
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
