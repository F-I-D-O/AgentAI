/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.bwapiCommandInterface;

import ninja.fido.agentSCAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
abstract class BwapiCommad<A extends GameAgent> {
	private final A agent;

	public A getAgent() {
		return agent;
	}
	
	
	

	public BwapiCommad(A agent) {
		this.agent = agent;
	}
	
	
	
	public abstract String getType();
}
