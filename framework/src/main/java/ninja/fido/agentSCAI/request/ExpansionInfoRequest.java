/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.request;

import ninja.fido.agentSCAI.base.Request;
import ninja.fido.agentSCAI.agent.ExplorationCommand;
import ninja.fido.agentSCAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfoRequest extends Request{
	
	public ExpansionInfoRequest(ExplorationCommand recipient, Agent sender) {
		super(recipient, sender);
	}
	
}
