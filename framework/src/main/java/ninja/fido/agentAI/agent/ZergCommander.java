/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;


import ninja.fido.agentAI.activity.zerg.OutbreakStrategy;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.OutbreakStrategyGoal;
import java.util.TreeMap;
import ninja.fido.agentAI.base.exception.CommanderNotCreatedException;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;

/**
 *
 * @author F.I.D.O.
 */
public class ZergCommander extends FullCommander{
	
	private static ZergCommander zergCommander;
	
	
	
	public static ZergCommander create(String name) throws MultipleCommandersException{
		zergCommander = new ZergCommander(name);
		return zergCommander;
	}
	
	public static ZergCommander get() throws CommanderNotCreatedException{
		if(zergCommander == null){
			throw new CommanderNotCreatedException(ZergCommander.class);
		}
		return zergCommander;
	}
	
	
	
	public final LarvaCommand larvaCommand;
	
	public final ExpansionCommand expansionCommand;
	
	protected ZergCommander(String name) throws MultipleCommandersException {
		super(name);
		larvaCommand = new LarvaCommand();
		GameAPI.addAgent(larvaCommand, this);
		
		expansionCommand = new ExpansionCommand();
		GameAPI.addAgent(expansionCommand, this);
		
		reasoningOn = true;
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
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
