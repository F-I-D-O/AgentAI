/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base.exception;

import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Order;

/**
 *
 * @author F.I.D.O.
 */
public class ChainOfCommandViolationException extends Exception{
	private final Agent violator;
	
	private final Order order;
	
	private final Agent target;

	public ChainOfCommandViolationException(Agent violator, Order order, Agent target) {
		this.violator = violator;
		this.order = order;
		this.target = target;
	}
	
	
	
	@Override
	public String getMessage() {
		return "Chain of command has been violated by " + violator.getClass() + ". " + target.getClass()
				+ " is not under it|s command (order: " + order + ").";
	}
}
