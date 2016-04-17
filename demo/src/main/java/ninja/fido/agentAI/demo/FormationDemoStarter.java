/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo;

import java.io.IOException;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentAI.activity.ASAPSquadAttackMove;
import ninja.fido.agentAI.activity.NormalSquadAttackMove;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.exception.ModuleDependencyException;
import static ninja.fido.agentAI.demo.Starter.getFullCommanderDefaultDecisionTablesMap;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentAI.modules.decisionStorage.DecisionStorageModule;
import org.xml.sax.SAXException;

/**
 *
 * @author david
 */
public class FormationDemoStarter {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException, 
			ModuleDependencyException {
		GameAPI gameAPI = new GameAPI(Level.FINE, 0, 0);
		
		DecisionModule decisionModule = new DecisionModule();
		
		decisionModule.registerCommanderType(FullCommander.class, getFullCommanderDefaultDecisionTablesMap());
		decisionModule.registerAgentClass(new SquadCommander());
		
//		DecisionStorageModule decisionStorageModule = new DecisionStorageModule(decisionModule);
//		
//		decisionStorageModule.registerActivity(new Wait());
//		decisionStorageModule.registerActivity(new ASAPSquadAttackMove());
//		decisionStorageModule.registerActivity(new NormalSquadAttackMove());
//		
//		decisionStorageModule.registerParameter(new GoalParameter(null));
		
//		LearningModule learningModule = new LearningModule(gameAPI, decisionModule, decisionStorageModule);
//		learningModule.setLearningScenario(new SquadAttackScenario());
		
		gameAPI.registerModule(decisionModule);
//		gameAPI.registerModule(decisionStorageModule);
//		gameAPI.registerModule(learningModule);
		
//		learningModule.processResults();
		
		setZealotGroupActivityMap
		
        gameAPI.run();
    }
}
