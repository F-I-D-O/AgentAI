/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base;

import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 * This class represent order.
 * @author F.I.D.O.
 * @param <T> Command target type. When you subclass order, you should restrict the target type as much as it 
 * is possible.
 */
public abstract class Order<T extends Agent> {
	
	/**
	 * Command agent issuing the order.
	 */
	protected final CommandAgent commandAgent;
	
	/**
	 * Order target.
	 */
	private final T target;

	
	
	
	/**
	 * Order constructor
	 * @param target Target of the order.
	 * @param commandAgent Command agent issuing the order.
	 * @throws ChainOfCommandViolationException 
	 */
	public Order(T target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		if(!commandAgent.getCommandedAgents().contains(target)){
			throw new ChainOfCommandViolationException(commandAgent, this, target);
		}
		this.target = target;
		this.commandAgent = commandAgent;
	}
	
	
	
	/**
	 * This function issues the order to target agent
	 */
	public final void issueOrder(){
		target.addToOrderQueue(this);
		commandAgent.addUncompletedOrder(this);
	}
	
	/**
	 * Report that the command is completed to command agent
	 */
	public void reportCompleted(){
		commandAgent.reportOrderCompleted(this);
	}
	
	/**
	 * Returns the target of the command.
	 * @return Target of the command.
	 */
	public T getTarget(){
		return target;
	}
	
	
	/**
	 * Body of the command. If it's goal command, goal should be set here. Otherwise there could be any code. 
	 * There shouldn't be any complex code though.
	 */
	protected abstract void execute();	
	
}
