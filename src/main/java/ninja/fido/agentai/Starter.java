/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import java.io.IOException;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentai.activity.ASAPSquadAttackMove;
import ninja.fido.agentai.activity.NormalSquadAttackMove;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.agent.FullCommander;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.modules.decisionStorage.DecisionStorageModule;
import ninja.fido.agentai.modules.learning.LearningModule;
import ninja.fido.agentai.modules.decisionMaking.DecisionModule;
import ninja.fido.agentai.modules.decisionMaking.GoalParameter;
import ninja.fido.agentai.modules.learning.SquadAttackScenario;
import org.xml.sax.SAXException;

/**
 *
 * @author F.I.D.O.
 */
public class Starter {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException {
		GameAPI gameAPI = new GameAPI(Level.FINE, 0, 0);
		
		DecisionModule decisionModule = new DecisionModule();
		
		decisionModule.registerCommanderType(FullCommander.class, FullCommander.getDefaultDecisionTablesMapStatic());
		decisionModule.registerAgentClass(new SquadCommander());
		
		DecisionStorageModule decisionStorageModule = new DecisionStorageModule(decisionModule);
		
		decisionStorageModule.registerActivity(new Wait());
		decisionStorageModule.registerActivity(new ASAPSquadAttackMove());
		decisionStorageModule.registerActivity(new NormalSquadAttackMove());
		
		decisionStorageModule.registerParameter(new GoalParameter(null));
		
//		LearningModule learningModule = new LearningModule(gameAPI, decisionModule, decisionStorageModule);
//		learningModule.setLearningScenario(new SquadAttackScenario());
		
		gameAPI.registerModule(decisionModule);
		gameAPI.registerModule(decisionStorageModule);
//		gameAPI.registerModule(learningModule);
		
//		learningModule.processResults();
		
        gameAPI.run();
    }
}
