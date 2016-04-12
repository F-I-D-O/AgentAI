/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
