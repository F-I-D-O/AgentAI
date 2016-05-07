/* 
 * AgentAI
 */
package ninja.fido.agentAI.modules.learning;

import ninja.fido.agentAI.GameResult;
import ninja.fido.agentAI.UnitDecisionSetting;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public interface LearningEngine {
	public List<UnitDecisionSetting> learnFromResults(ArrayList<GameResult> gameResults);
}
