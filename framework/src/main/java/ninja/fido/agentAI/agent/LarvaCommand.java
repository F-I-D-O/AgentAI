/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.activity.zerg.OutbreakProduction;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.goal.DroneProductionGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaCommand extends CommandAgent{

	public LarvaCommand() {
		
		reasoningOn = true;
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new OutbreakProduction());
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
