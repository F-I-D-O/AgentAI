/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import ninja.fido.agentSCAI.activity.ASAPSquadAttackMove;
import ninja.fido.agentSCAI.activity.NormalSquadAttackMove;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.goal.SquadAttackMoveGoal;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentSCAI.modules.decisionMaking.GoalParameter;

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
