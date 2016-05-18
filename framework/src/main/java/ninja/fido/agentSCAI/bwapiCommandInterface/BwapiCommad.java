/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.bwapiCommandInterface;

import ninja.fido.agentSCAI.base.GameAgent;

/**
 * BWAPI command wrapper.
 * @author F.I.D.O.
 */
abstract class BwapiCommad<A extends GameAgent> {
	
	/**
	 * Game agent that is affected by the command.
	 */
	private final A agent;

	/**
	 * Returns the target of the BWAPI command.
	 * @return Returns the target of the BWAPI command.
	 */
	public A getAgent() {
		return agent;
	}
	
	
	
	/**
	 * Constructor.
	 * @param agent 
	 */
	public BwapiCommad(A agent) {
		this.agent = agent;
	}
	
	
	
	
	/**
	 * 
	 * @return 
	 */
	public abstract String getType();
}
