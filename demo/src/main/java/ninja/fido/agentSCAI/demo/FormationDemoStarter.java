/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentSCAI.activity.Move;
import ninja.fido.agentSCAI.agent.FullCommander;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.agent.unit.Zealot;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.exception.ModuleDependencyException;
import ninja.fido.agentSCAI.base.exception.MultipleCommandersException;
import ninja.fido.agentSCAI.demo.activity.protoss.FormationTestSquadFormation;
import ninja.fido.agentSCAI.demo.activity.protoss.FormationTestSquadFormationIndividual;
import ninja.fido.agentSCAI.demo.activity.protoss.FormationTestStrategy;
import ninja.fido.agentSCAI.demo.activity.protoss.GroupGuard;
import ninja.fido.agentSCAI.demo.goal.FormationTestSquadFormationGoal;
import ninja.fido.agentSCAI.demo.goal.FormationTestSquadFormationIndividualGoal;
import ninja.fido.agentSCAI.demo.goal.FormationTestStrategyGoal;
import ninja.fido.agentSCAI.demo.goal.GroupGuardGoal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;
import org.xml.sax.SAXException;

/**
 * Formation demo starter
 * @author david
 */
public class FormationDemoStarter {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException, 
			ModuleDependencyException, MultipleCommandersException, EmptyDecisionTableMapException {
		FullCommander commander = new FullCommander("Formation Test", new FormationTestStrategyGoal(null, null));
		
		GameAPI gameAPI = new GameAPI(Level.FINE, 30, 0, commander);
		
		setGoalActivityMaps();
		
        gameAPI.run();
    }

	private static void setGoalActivityMaps() throws EmptyDecisionTableMapException {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new Zealot().getDefaultGoalActivityMap();
		defaultActivityMap.put(GroupGuardGoal.class, new GroupGuard());
		defaultActivityMap.put(MoveGoal.class, new Move());
		GameAPI.addSimpleDecisionMap(Zealot.class, defaultActivityMap);
		
		defaultActivityMap = new SquadCommander().getDefaultGoalActivityMap();
		defaultActivityMap.put(FormationTestSquadFormationGoal.class, new FormationTestSquadFormation());
		defaultActivityMap.put(
				FormationTestSquadFormationIndividualGoal.class, new FormationTestSquadFormationIndividual());
		defaultActivityMap.put(MoveGoal.class, new Move());
		GameAPI.addSimpleDecisionMap(SquadCommander.class, defaultActivityMap);
		
		defaultActivityMap = new HashMap<>();
		defaultActivityMap.put(FormationTestStrategyGoal.class, new FormationTestStrategy());
		GameAPI.addSimpleDecisionMap(FullCommander.class, defaultActivityMap);
	}
	
//	private static Map<DecisionTablesMapKey, DecisionTable> getFullCommanderDefaultDecisionTablesMap() {
//		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
//		
//		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
//		actionMap.put(1.0, new DefaultProtossStrategy());
//		DecisionTablesMapKey key =  new DecisionTablesMapKey();
//		key.addParameter(new GoalParameter(DefaultProtossStrategyGoal.class));
//		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
//		
//		return defaultDecisionTablesMap;
//	}
}
