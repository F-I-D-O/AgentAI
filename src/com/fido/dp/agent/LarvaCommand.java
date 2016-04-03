/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.activity.zerg.OutbreakProduction;
import com.fido.dp.base.Activity;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.DroneProductionGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaCommand extends CommandAgent{

	public LarvaCommand() {
		
		reasoningOn = true;
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new OutbreakProduction(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(DroneProductionGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}
	
	

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new DroneProductionGoal(this, null);
	}
	
}
