/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.MorphableUnit;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.decisionMaking.DecisionTable;
import ninja.fido.agentai.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.WaitGoal;
import java.util.TreeMap;
import java.util.logging.Level;

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
	

	public Larva(Unit unit) {
		super(unit);
		
		reasoningOn = true;
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}
	
	
	
	public void morph(MorphOption morphOption) throws ResourceDeficiencyException{
		Log.log(this, Level.INFO, "{0}: morphing into: {1}", this.getClass(), morphOption.unitType);
		spendSupply(ResourceType.MINERALS, morphOption.unitType.mineralPrice());
		spendSupply(ResourceType.GAS, morphOption.unitType.gasPrice());
		spendSupply(ResourceType.SUPPLY, morphOption.unitType.supplyRequired());
		unit.morph(morphOption.unitType);
	}
	

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
