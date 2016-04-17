/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.unit.Zealot;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.exception.ModuleDependencyException;
import ninja.fido.agentAI.demo.activity.protoss.DefaultProtossStrategy;
import ninja.fido.agentAI.demo.activity.protoss.GroupGuard;
import ninja.fido.agentAI.goal.DefaultProtossStrategyGoal;
import ninja.fido.agentAI.goal.GroupGuardGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
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
//		
		decisionModule.registerCommanderType(FullCommander.class, getFullCommanderDefaultDecisionTablesMap());
//		decisionModule.registerAgentClass(new SquadCommander());
		
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
		
		setZealotGoalActivityMap();
		
        gameAPI.run();
    }

	private static void setZealotGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new Zealot().getDefaultGoalActivityMap();
		defaultActivityMap.put(GroupGuardGoal.class, new GroupGuard());
		defaultActivityMap.put(MoveGoal.class, new Move());
		GameAPI.addSimpleDecisionMap(Zealot.class, defaultActivityMap);
	}
	
	private static Map<DecisionTablesMapKey, DecisionTable> getFullCommanderDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new DefaultProtossStrategy());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(DefaultProtossStrategyGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
}
