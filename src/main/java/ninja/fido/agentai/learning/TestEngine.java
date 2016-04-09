/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.learning;

import ninja.fido.agentai.GameResult;
import ninja.fido.agentai.UnitDecisionSetting;
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
