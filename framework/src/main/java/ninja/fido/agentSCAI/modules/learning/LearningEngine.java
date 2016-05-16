/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.modules.learning;

import ninja.fido.agentSCAI.GameResult;
import ninja.fido.agentSCAI.UnitDecisionSetting;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public interface LearningEngine {
	public List<UnitDecisionSetting> learnFromResults(ArrayList<GameResult> gameResults);
}
