/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.modules.learning;

import java.util.Map;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.modules.decisionMaking.DecisionTable;
import ninja.fido.agentai.modules.decisionMaking.DecisionTablesMapKey;

/**
 *
 * @author F.I.D.O.
 */
public abstract class LearningScenario {

	protected abstract Map<Class<? extends Agent>, Map<DecisionTablesMapKey, DecisionTable>>
		 getDecisionSettings(int gameCount);
}
