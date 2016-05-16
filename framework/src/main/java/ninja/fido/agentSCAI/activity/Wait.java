/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.activity;

import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.modules.decisionStorage.StorableDecisionModuleActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 */

public class Wait extends Activity<Agent,Goal,Wait> implements StorableDecisionModuleActivity<Agent,Goal,Wait>{

	public Wait() {
	}
	
	public Wait(Agent agent) {
		super(agent);
	}
	
	

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof Wait;
	}
	
	
	
	

	@Override
	protected void performAction() {
		
	}

	@Override
	protected void init() {
		
	}

	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("wait");
		
		return parameter;
	}

	@Override
	public String getId() {
		return "wait";
	}

	@Override
	public Wait create(Agent agent, Goal goal) {
		return new Wait(agent);
	}

	@Override
	public StorableDecisionModuleActivity getFromXml(Element activityElement) {
		return new Wait();
	}
	
	
	
}
