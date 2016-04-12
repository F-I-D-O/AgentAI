/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.modules.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import ninja.fido.agentai.activity.ASAPSquadAttackMove;
import ninja.fido.agentai.activity.NormalSquadAttackMove;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.goal.SquadAttackMoveGoal;
import ninja.fido.agentai.goal.WaitGoal;
import ninja.fido.agentai.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.modules.decisionMaking.DecisionTable;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.modules.decisionMaking.GoalParameter;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackScenario extends LearningScenario{

	@Override
	protected Map<Class<? extends Agent>, Map<DecisionTablesMapKey, DecisionTable>> getDecisionSettings(int gameCount) {
		Map<Class<? extends Agent>,Map<DecisionTablesMapKey,DecisionTable>> decisionSettings = new HashMap<>();
		
		Map<DecisionTablesMapKey, DecisionTable> decisionTablesMap = new HashMap<>();
		
		int minSquadSize = gameCount % 7;
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new NormalSquadAttackMove(minSquadSize));
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(SquadAttackMoveGoal.class));
		decisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(WaitGoal.class));
		decisionTablesMap.put(key, new DecisionTable(actionMap));
		
		decisionSettings.put(SquadCommander.class, decisionTablesMap);
		
		return decisionSettings;
	}
	
}
