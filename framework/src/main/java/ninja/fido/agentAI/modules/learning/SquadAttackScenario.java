/* 
 * AgentAI
 */
package ninja.fido.agentAI.modules.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import ninja.fido.agentAI.activity.ASAPSquadAttackMove;
import ninja.fido.agentAI.activity.NormalSquadAttackMove;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.goal.SquadAttackMoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;

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
