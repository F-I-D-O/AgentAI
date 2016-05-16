/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.modules.decisionMaking;

import ninja.fido.agentSCAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class EmptyDecisionTableMapException extends Exception {
	private final Class<? extends Agent> agentClass;

	public EmptyDecisionTableMapException(Class<? extends Agent> agentClass) {
		this.agentClass = agentClass;
	}
	
	@Override
	public String getMessage() {
		return agentClass + ": Decision tables map is empty, cannot generate reference key!";
	}
}
