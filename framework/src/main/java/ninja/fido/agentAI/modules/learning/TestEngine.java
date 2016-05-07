/* 
 * AgentAI
 */
package ninja.fido.agentAI.modules.learning;

import ninja.fido.agentAI.GameResult;
import ninja.fido.agentAI.UnitDecisionSetting;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class TestEngine implements LearningEngine{

	@Override
	public ArrayList<UnitDecisionSetting> learnFromResults(ArrayList<GameResult> gameResults) {
		ArrayList<UnitDecisionSetting> unitDecisionSettings = new ArrayList<>();
		unitDecisionSettings.add(gameResults.get(0).getUnitDecisionSettings().get(0));
		return unitDecisionSettings;
	}
	
}
