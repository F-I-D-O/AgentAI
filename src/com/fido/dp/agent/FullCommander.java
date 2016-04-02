/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Race;
import com.fido.dp.activity.protoss.DefaultProtossStrategy;
import com.fido.dp.activity.protoss.FormationTestStrategy;
import com.fido.dp.activity.terran.BBSStrategy;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import com.fido.dp.decisionMaking.GoalParameter;
import com.fido.dp.goal.BBSStrategyGoal;
import com.fido.dp.goal.DefaultProtossStrategyGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class FullCommander extends Commander{
	
	private static FullCommander fullCommander;
	
	public static FullCommander get(){
		return fullCommander;
	}
	
	
	
	
	
	public final ExplorationCommand explorationCommand;
	
	public final ResourceCommand resourceCommand;

	public final BuildCommand buildCommand;
	
	
	
	public ExplorationCommand getExplorationCommand() {
		return explorationCommand;
	}
	
	
	
	
	

	public FullCommander() {
		explorationCommand = new ExplorationCommand();
		GameAPI.addAgent(explorationCommand, this);
		resourceCommand = new ResourceCommand();
		GameAPI.addAgent(resourceCommand, this);
		buildCommand = new BuildCommand();
		GameAPI.addAgent(resourceCommand, this);
		
//		reasoningOn = true;
		
//		TreeMap<Double,Activity> actionMap = new TreeMap<>();
//		actionMap.put(1.0, new BBSStrategy(this));
//		DecisionTablesMapKey key =  new DecisionTablesMapKey();
//		key.addParameter(new RaceParameter(Race.Terran));
//		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
//		actionMap = new TreeMap<>();
//		actionMap.put(1.0, new OutbreakStrategy(this));
//		key =  new DecisionTablesMapKey();
//		key.addParameter(new RaceParameter(Race.Zerg));
//		addToDecisionTablesMap(key, new DecisionTable(actionMap));

		TreeMap<Double,Activity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new BBSStrategy(this));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(BBSStrategyGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new DefaultProtossStrategy(this));
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(DefaultProtossStrategyGoal.class));
		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
		referenceKey = key;
		
		fullCommander = this;
	}
	
	@Override
	protected Goal getDefaultGoal() {
		return (GameAPI.getGame().self().getRace().equals(Race.Terran) ? new BBSStrategyGoal(this, null)
				: new DefaultProtossStrategyGoal(this, null));
	}

	@Override
	protected void initialize() {
		
	}

	@Override
	protected Activity chooseAction() {
		return new FormationTestStrategy(this);
	}
	
	
}
