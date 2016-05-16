/* 
 * AgentAI - Demo
 */
package ninja.fido.AgentSCAI.emptyai;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentAI.agent.BuildCommand;
import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Commander;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;
import org.xml.sax.SAXException;


/**
 * Modules demo starter.
 * @author F.I.D.O.
 */
public class Starter {
	public static void main(String[] args) throws MultipleCommandersException, EmptyDecisionTableMapException, 
			SAXException, IOException, ParserConfigurationException, ClassNotFoundException, TransformerException, 
			TransformerConfigurationException, XPathExpressionException{
		Commander commander = new Commander("Empty AI", new WaitGoal(null, null));
		
		GameAPI gameAPI = new GameAPI(Level.FINE, 20, 0, commander);
		
//		DecisionModule decisionModule = new DecisionModule();
		
//		decisionModule.registerCommanderType(FullCommander.class, getFullCommanderDefaultDecisionTablesMap());
//		decisionModule.registerAgentClass(new SquadCommander());
		
//		gameAPI.registerModule(decisionModule);	

		setGoalActivityMaps();
		
        gameAPI.run();
    }
	
	private static void setGoalActivityMaps() throws EmptyDecisionTableMapException {
//		Map<Class<? extends Goal>,Activity> defaultActivityMap = new BuildCommand().getDefaultGoalActivityMap();
//		defaultActivityMap.put(BBSBuildGoal.class, new BBSBuild());
//		GameAPI.addSimpleDecisionMap(BuildCommand.class, defaultActivityMap);
	}
	
//	private static Map<DecisionTablesMapKey, DecisionTable> getFullCommanderDefaultDecisionTablesMap() {
//		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
//		
////		TreeMap<Double,Activity> actionMap = new TreeMap<>();
////		actionMap.put(1.0, new BBSStrategy(this));
////		DecisionTablesMapKey key =  new DecisionTablesMapKey();
////		key.addParameter(new RaceParameter(Race.Terran));
////		addToDecisionTablesMap(key, new DecisionTable(actionMap));
//		
////		actionMap = new TreeMap<>();
////		actionMap.put(1.0, new OutbreakStrategy(this));
////		key =  new DecisionTablesMapKey();
////		key.addParameter(new RaceParameter(Race.Zerg));
////		addToDecisionTablesMap(key, new DecisionTable(actionMap));
//
//		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
//		actionMap.put(1.0, new BBSStrategy());
//		DecisionTablesMapKey key =  new DecisionTablesMapKey();
//		key.addParameter(new GoalParameter(BBSStrategyGoal.class));
//		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
//		
//		actionMap = new TreeMap<>();
//		actionMap.put(1.0, new DefaultProtossStrategy());
//		key =  new DecisionTablesMapKey();
//		key.addParameter(new GoalParameter(DefaultProtossStrategyGoal.class));
//		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
//		
//		return defaultDecisionTablesMap;
//	}
}
