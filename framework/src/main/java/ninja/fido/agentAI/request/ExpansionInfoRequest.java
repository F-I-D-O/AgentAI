/* 
 * AgentAI
 */
package ninja.fido.agentAI.request;

import ninja.fido.agentAI.base.Request;
import ninja.fido.agentAI.agent.ExplorationCommand;
import ninja.fido.agentAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfoRequest extends Request{
	
	public ExpansionInfoRequest(ExplorationCommand recipient, Agent sender) {
		super(recipient, sender);
	}
	
}
