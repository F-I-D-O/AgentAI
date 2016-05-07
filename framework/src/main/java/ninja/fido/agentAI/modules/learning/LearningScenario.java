/* 
 * AgentAI
 */
package ninja.fido.agentAI.modules.learning;

import java.util.Map;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;

/**
 *
 * @author F.I.D.O.
 */
public abstract class LearningScenario {

	protected abstract Map<Class<? extends Agent>, Map<DecisionTablesMapKey, DecisionTable>>
		 getDecisionSettings(int gameCount);
}
