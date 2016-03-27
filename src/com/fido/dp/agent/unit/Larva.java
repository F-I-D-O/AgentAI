/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.Log;
import com.fido.dp.MorphableUnit;
import com.fido.dp.ResourceType;
import com.fido.dp.activity.Wait;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.WaitGoal;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class Larva extends UnitAgent implements MorphableUnit{

	@Override
	public void onMorphFinished() {
		Log.log(this, Level.INFO, "{0}: morph into {1} finished.", this.getClass(), unit.getType());
	}
	
	public enum MorphOption {
		DRONE(UnitType.Zerg_Drone);
		
		private final UnitType unitType;
		
		MorphOption(UnitType unitType){
			this.unitType = unitType;
		}
	}
	

	public Larva(Unit unit) {
		super(unit);
		
		reasoningOn = true;
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}
	
	
	
	public void morph(MorphOption morphOption){
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
