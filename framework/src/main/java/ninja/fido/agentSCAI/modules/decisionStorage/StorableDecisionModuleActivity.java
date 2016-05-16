/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.decisionStorage;

import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 * @param <A> Agent
 * @param <G> Goal
 * @param <AC> Activity
 */
public interface StorableDecisionModuleActivity<A extends Agent,G extends Goal,AC extends Activity> 
		extends DecisionModuleActivity<A, G, AC>{
	
	public Element getXml(Document document);
	
	public String getId();

	public StorableDecisionModuleActivity getFromXml(Element activityElement);
}
