/* 
 * AgentAI
 */
package ninja.fido.agentAI.base;

import java.util.ArrayList;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 * Order for command agent. Contains some methods that can acces target like a command agent.
 * @author david
 * @param <T> Command agent type.
 */
public abstract class OrderForCommandAgent<T extends CommandAgent> extends Order<T>{

	
	/**
	 * Constructor
	 * @param target Target command agent.
	 * @param commandAgent Command agent that issued this order.
	 * @throws ChainOfCommandViolationException 
	 */
	public OrderForCommandAgent(T target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	
	
	
	/**
	 * Returns list of agents under direct command.
	 * @return Returns list of agents under direct command.
	 */
    protected ArrayList<Agent> getCommandedAgents() {
        return getTarget().getCommandedAgents();
    }
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
	protected final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
		getTarget().detachCommandedAgent(subordinateAgent, newCommand);
	}
	
	/**
	 * Detaches commanded agents to other agent.
	 * @param subordinateAgents agent to be detached.
	 * @param newCommand new command agent for agents.
	 */
	protected final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		getTarget().detachCommandedAgents(subordinateAgents, newCommand);
	}
	
	/**
	 * Get commanded age by type. 
	 * @param <T> type of agent we want to get.
	 * @param agentClass class specifying agent type.
	 * @return Returns commanded agent specified by type.
	 */
    protected final <T> T getCommandedAgent(Class<T> agentClass) {
		return getTarget().getCommandedAgent(agentClass);
	}
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @return Returns all commanded agents of specified type.
	 */
    protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass) {
		return getTarget().getCommandedAgents(agentClass);
	}
	
	/**
	 * Get all commanded agents of scpecified type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @param idleOnly By this parameter, you can specify to return only agents that are not assigned. true means not 
	 * assigned only, false means all.
	 * @return Returns all commanded agents of specified type. If {@code idleOnly} is true, this method returns only 
	 * agents that are not assigned.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, boolean idleOnly) {
		return getTarget().getCommandedAgents(agentClass, idleOnly);
    }
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent you want to get.
	 * @param agentClass Class specifying agent type.
	 * @param count Number of agents you need.
	 * @return Returns commanded agents of specified type {@code T}. Number of agents returned is specified by 
	 * {@code count}.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count) {
		return getTarget().getCommandedAgents(agentClass, count);
	}
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent you want to get.
	 * @param agentClass Class specifying agent type.
	 * @param count Number of agents you need.
	 * @param idleOnly By this parameter, you can specify to return only agents that are not assigned. true means not 
	 * assigned only, false means all.
	 * @return Returns commanded agents of specified type {@code T}. Number of agents returned is specified by 
	 * {@code count}. If {@code idleOnly} is true, this method returns only agents that are not assigned.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count, boolean idleOnly) {
		return getTarget().getCommandedAgents(agentClass, count, idleOnly);
	}
	
	/**
	 * Returns nuber of agents under direct command.
	 * @return Returns nuber of agents under direct command.
	 */
	protected final int getNumberOfCommandedAgents(){
		return getTarget().getNumberOfCommandedAgents();
	}   
	
	/**
	 * Tells whether some specified commanded agent is occupied. IE this agent issueed an order to the agent, and the order 
	 * hasn't been completed yet.
	 * @param agent Agent.
	 * @return True if agent is occupied, faalse otherwise.
	 */
	protected final boolean isCommandedAgentOccupied(Agent agent){
		return this.getTarget().isCommandedAgentOccupied(agent);
	}
	
	/**
	 * Returns the number of suboordinate agents detached to specified command agent.
	 * @param commandAgent Command aagent.
	 * @param agentClass ssubordinate agent type.
	 * @return Returns the number of suboordinate agents detached to specified command agent.
	 */
	protected final int getSubordinateAgentsDetachedTo(CommandAgent commandAgent, Class<? extends Agent> agentClass){
		return getTarget().getSubordinateAgentsDetachedTo(commandAgent, agentClass);
	}
	
	/**
	 * Returns amount of minerals given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of minerals given to specified agent.
	 */
	protected final int getMineralsGivenTo(Agent receiver){
		return getTarget().getMineralsGivenTo(receiver);
	}
	
}
