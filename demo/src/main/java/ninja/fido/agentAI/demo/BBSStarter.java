/* 
 * AgentAI - Demo
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
import ninja.fido.agentAI.agent.BuildCommand;
import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.agent.ProductionCommand;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.UnitCommand;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.exception.ModuleDependencyException;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;
import ninja.fido.agentAI.demo.activity.terran.BBSAttack;
import ninja.fido.agentAI.demo.activity.terran.BBSBuild;
import ninja.fido.agentAI.demo.activity.terran.BBSProduction;
import ninja.fido.agentAI.demo.activity.terran.BBSStrategy;
import ninja.fido.agentAI.demo.goal.BBSAttackGoal;
import ninja.fido.agentAI.demo.goal.BBSBuildGoal;
import ninja.fido.agentAI.demo.goal.BBSProductionGoal;
import ninja.fido.agentAI.demo.goal.BBSStrategyGoal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import org.xml.sax.SAXException;

/**
 * Terran demo starter.
 * @author F.I.D.O.
 */
public class BBSStarter {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException, 
			ModuleDependencyException, MultipleCommandersException, EmptyDecisionTableMapException {
		FullCommander commander = new FullCommander("BBS Demo", new BBSStrategyGoal(null, null));
		GameAPI gameAPI = new GameAPI(Level.FINE, 25, 0, commander);
		
		DecisionModule decisionModule = new DecisionModule();
		
		decisionModule.registerCommanderType(FullCommander.class, getFullCommanderDefaultDecisionTablesMap());
		decisionModule.registerAgentClass(new SquadCommander());
		
		gameAPI.registerModule(decisionModule);
		
		setGoalActivityMaps();
		
        gameAPI.run();
    }

	private static void setGoalActivityMaps() throws EmptyDecisionTableMapException {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new BuildCommand().getDefaultGoalActivityMap();
		defaultActivityMap.put(BBSBuildGoal.class, new BBSBuild());
		GameAPI.addSimpleDecisionMap(BuildCommand.class, defaultActivityMap);
		
		defaultActivityMap = new ProductionCommand().getDefaultGoalActivityMap();
		defaultActivityMap.put(BBSProductionGoal.class, new BBSProduction());
		GameAPI.addSimpleDecisionMap(ProductionCommand.class, defaultActivityMap);
		
		defaultActivityMap = new UnitCommand().getDefaultGoalActivityMap();
		defaultActivityMap.put(BBSAttackGoal.class, new BBSAttack());
		GameAPI.addSimpleDecisionMap(UnitCommand.class, defaultActivityMap);
	}
	
	private static Map<DecisionTablesMapKey, DecisionTable> getFullCommanderDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();

		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new BBSStrategy());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(BBSStrategyGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
}
