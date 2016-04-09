/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import bwapi.Race;
import ninja.fido.agentai.activity.protoss.DefaultProtossStrategy;
import ninja.fido.agentai.activity.protoss.FormationTestStrategy;
import ninja.fido.agentai.activity.terran.BBSStrategy;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.decisionMaking.DecisionTable;
import ninja.fido.agentai.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.decisionMaking.GoalParameter;
import ninja.fido.agentai.goal.BBSStrategyGoal;
import ninja.fido.agentai.goal.DefaultProtossStrategyGoal;
import java.util.HashMap;
import java.util.Map;
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
	
	
	
	
	
	public ExplorationCommand explorationCommand;
	
	public ResourceCommand resourceCommand;

	public BuildCommand buildCommand;
	
	
	
	public ExplorationCommand getExplorationCommand() {
		return explorationCommand;
	}

	
	
	
	public FullCommander() {
		
	}
	
	
	
	
	@Override
	protected Goal getDefaultGoal() {
		return (GameAPI.getGame().self().getRace().equals(Race.Terran) ? new BBSStrategyGoal(this, null)
				: new DefaultProtossStrategyGoal(this, null));
	}

	@Override
	protected void initialize() {
		explorationCommand = new ExplorationCommand();
		GameAPI.addAgent(explorationCommand, this);
		resourceCommand = new ResourceCommand();
		GameAPI.addAgent(resourceCommand, this);
		buildCommand = new BuildCommand();
		GameAPI.addAgent(buildCommand, this);
		
		fullCommander = this;
	}

	@Override
	protected Activity chooseAction() {
		return new FormationTestStrategy(this);
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
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

		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new BBSStrategy());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(BBSStrategyGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new DefaultProtossStrategy());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(DefaultProtossStrategyGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
	
}
