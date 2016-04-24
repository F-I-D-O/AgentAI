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
public class OrderForCommandAgent<T extends CommandAgent> extends Order<T>{

	
	
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
	
	
	@Override
	protected void execute() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
