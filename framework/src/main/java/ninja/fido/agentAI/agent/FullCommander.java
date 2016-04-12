/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.base.Commander;
import bwapi.Race;
import ninja.fido.agentAI.activity.protoss.DefaultProtossStrategy;
import ninja.fido.agentAI.activity.protoss.FormationTestStrategy;
import ninja.fido.agentAI.activity.terran.BBSStrategy;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.BBSStrategyGoal;
import ninja.fido.agentAI.goal.DefaultProtossStrategyGoal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import ninja.fido.agentAI.base.exception.CommanderNotCreatedException;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;

/**
 *
 * @author F.I.D.O.
 */
public class FullCommander extends Commander{
	
	private static FullCommander fullCommander;
	
	
	
	public static FullCommander create(String name) throws MultipleCommandersException{
		fullCommander = new FullCommander(name);
		return fullCommander;
	}
	
	public static FullCommander get() throws CommanderNotCreatedException{
		if(fullCommander == null){
			throw new CommanderNotCreatedException(FullCommander.class);
		}
		return fullCommander;
	}
	

	public static Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMapStatic() {
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
	
	
	
	
	
	public ExplorationCommand explorationCommand;
	
	public ResourceCommand resourceCommand;

	public BuildCommand buildCommand;


	
	
	
	private ExplorationCommand getExplorationCommand() {
		return explorationCommand;
	}

	
	
	
	protected FullCommander(String name) throws MultipleCommandersException {
		super(name);
	}

	
	
	
	@Override
	protected Goal getDefaultGoal() {
		return (GameAPI.getGame().self().getRace().equals(Race.Terran) ? new BBSStrategyGoal(this, null)
				: new DefaultProtossStrategyGoal(this, null));
	}

	@Override
	protected void init() {
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

	
	
	
}
