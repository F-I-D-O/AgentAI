/* 
 * AgentAI - Demo
 */
package ninja.fido.agentSCAI.demo;

import bwapi.UnitType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentAI.GameAPIListener;
import ninja.fido.agentAI.agent.ExpansionCommand;
import ninja.fido.agentAI.agent.LarvaCommand;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.ZergCommander;
import ninja.fido.agentAI.agent.unit.Drone;
import ninja.fido.agentAI.agent.unit.Larva;
import ninja.fido.agentAI.agent.unit.Overlord;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.exception.ModuleDependencyException;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;
import ninja.fido.agentSCAI.demo.activity.zerg.OutbreakProduction;
import ninja.fido.agentSCAI.demo.activity.zerg.OutbreakStrategy;
import ninja.fido.agentAI.goal.DroneProductionGoal;
import ninja.fido.agentSCAI.demo.goal.OutbreakStrategyGoal;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;
import ninja.fido.agentAI.modules.decisionMaking.GoalParameter;
import org.xml.sax.SAXException;

/**
 * Zerg demo starter.
 * @author F.I.D.O.
 */
public class OutbreakStarter implements GameAPIListener{
	
	
	GameAPI gameAPI;

	public OutbreakStarter() throws MultipleCommandersException, EmptyDecisionTableMapException, SAXException, 
			IOException, ParserConfigurationException, ClassNotFoundException, TransformerException,
			TransformerConfigurationException, XPathExpressionException, ModuleDependencyException {
		ZergCommander commander = new ZergCommander("Outbreak Demo", new OutbreakStrategyGoal(null, null));
		gameAPI = new GameAPI(Level.FINE, 0, 0, commander);
		
		DecisionModule decisionModule = new DecisionModule();
		
		decisionModule.registerCommanderType(ZergCommander.class, getFullCommanderDefaultDecisionTablesMap());
		decisionModule.registerAgentClass(new SquadCommander());
		decisionModule.registerAgentClass(new LarvaCommand());
		decisionModule.registerAgentClass(new ExpansionCommand());
		decisionModule.registerAgentClass(new Drone());
		decisionModule.registerAgentClass(new Larva());
		decisionModule.addDecisionTablesMap(LarvaCommand.class, getLarvaCommandDecisionTablesMap());
		
		gameAPI.registerModule(decisionModule);
		
		gameAPI.registerListener(this);
		
        gameAPI.run();
	}
	
	
	
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException, 
			ModuleDependencyException, MultipleCommandersException, EmptyDecisionTableMapException {
		new OutbreakStarter();				
    }

	public static Map<DecisionTablesMapKey, DecisionTable> getLarvaCommandDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new OutbreakProduction());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(DroneProductionGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
	public static Map<DecisionTablesMapKey, DecisionTable> getFullCommanderDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new OutbreakStrategy());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(OutbreakStrategyGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}

	@Override
	public void onStart() {
		try {
			gameAPI.registerGameAgent(UnitType.Zerg_Drone, new Drone());
			gameAPI.registerGameAgent(UnitType.Zerg_Overlord, new Overlord());
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}
}
