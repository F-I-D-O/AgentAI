/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Race;
import com.fido.dp.activity.terran.BBSStrategy;
import com.fido.dp.activity.zerg.OutbreakStrategy;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.OutbreakStrategyGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class ZergCommander extends FullCommander{
	
	public final LarvaCommand larvaCommand;
	
	
	public ZergCommander() {
		larvaCommand = new LarvaCommand();
		GameAPI.addAgent(larvaCommand, this);
		
		reasoningOn = true;
		
		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new OutbreakStrategy(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(OutbreakStrategyGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new OutbreakStrategyGoal(this, null);
	}
	
	
}
