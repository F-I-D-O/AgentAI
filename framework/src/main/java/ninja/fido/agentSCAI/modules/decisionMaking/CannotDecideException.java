/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.decisionMaking;

import ninja.fido.agentSCAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class CannotDecideException extends Exception{
	
	private final Agent agent;
	
	private final DecisionTablesMapKey decisionTablesMapKey;

	
	
	
	public CannotDecideException(Agent agent, DecisionTablesMapKey decisionTablesMapKey) {
		this.agent = agent;
		this.decisionTablesMapKey = decisionTablesMapKey;
	}
	
	
	
	@Override
	public String getMessage() {
		return agent + ": Cannot decide what to do. Decision Table Key: " + decisionTablesMapKey;
	}
	
}
