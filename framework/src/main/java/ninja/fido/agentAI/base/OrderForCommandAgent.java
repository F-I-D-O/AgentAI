/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base;

import java.util.ArrayList;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author david
 * @param <T>
 */
public abstract class OrderForCommandAgent<T extends CommandAgent> extends Order<T>{

	
	
	public OrderForCommandAgent(T target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	
	
	
	/**
	 * Returns list of agents under direct command.
	 * @return Returns list of agents under direct command.
	 */
    public ArrayList<Agent> getCommandedAgents() {
        return getTarget().getCommandedAgents();
    }
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
	public final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
		getTarget().detachCommandedAgent(subordinateAgent, newCommand);
	}
	
	/**
	 * Detaches commanded agents to other agent.
	 * @param subordinateAgents agent to be detached.
	 * @param newCommand new command agent for agents.
	 */
	public final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		getTarget().detachCommandedAgents(subordinateAgents, newCommand);
	}
	
	/**
	 * Get commanded age by type. 
	 * @param <T> type of agent we want to get.
	 * @param agentClass class specifying agent type.
	 * @return Returns commanded agent specified by type.
	 */
    public final <T> T getCommandedAgent(Class<T> agentClass) {
		return getTarget().getCommandedAgent(agentClass);
	}
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @return Returns all commanded agents of specified type.
	 */
    public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass) {
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
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, boolean idleOnly) {
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
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count) {
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
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count, boolean idleOnly) {
		return getTarget().getCommandedAgents(agentClass, count, idleOnly);
	}
	
}
