/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.activity.Wait;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.WaitGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class Overlord extends UnitAgent{

	public Overlord(Unit unit) {
		super(unit);
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
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
