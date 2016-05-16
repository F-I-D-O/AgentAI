/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.modules.learning;

import java.util.Map;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;

/**
 *
 * @author F.I.D.O.
 */
public abstract class LearningScenario {

	protected abstract Map<Class<? extends Agent>, Map<DecisionTablesMapKey, DecisionTable>>
		 getDecisionSettings(int gameCount);
}
