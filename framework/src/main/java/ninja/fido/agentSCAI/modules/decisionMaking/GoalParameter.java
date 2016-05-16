/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.decisionMaking;

import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 */
public class GoalParameter extends DecisionTablesMapParameter<Agent,Class<? extends Goal>,GoalParameter>{

	public GoalParameter(Class<? extends Goal> value) {
		super(value);
	}

	@Override
	public GoalParameter getCurrentParameter(Agent agent) {
		return new GoalParameter(agent.getGoal().getClass());
	}

	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("goalParameter");
		parameter.setAttribute("value", getValue().getName());
		
		return parameter;
	}

	@Override
	public GoalParameter createFromXml(Element element) {
		try {
			return new GoalParameter((Class<? extends Goal>) Class.forName(element.getAttribute("value")));
		} 
		catch (ClassNotFoundException ex) {
			Log.log(this, Level.SEVERE, "Creating of goalParameter  from XML failed - class {0} not found", 
					element.getAttribute("value"));
		}
		return null;
	}

	@Override
	public String getId() {
		return "goalParameter";
	}
	
	

	
	
}
