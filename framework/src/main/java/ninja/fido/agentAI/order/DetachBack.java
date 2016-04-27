/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandAgent;
import java.util.List;
import ninja.fido.agentAI.base.OrderForCommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public class DetachBack extends OrderForCommandAgent<CommandAgent>{

	private Class agentType = null;
	
	private int numberOfUnits = 0;
	
	private boolean idleOnly = false;

	
	
	public Class getAgentType() {
		return agentType;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}
	
	
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.agentType = agentType;
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType, boolean idleOnly) 
			throws ChainOfCommandViolationException {
		this(target, commandAgent, agentType);
		this.idleOnly = idleOnly;
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType, int numberOfUnits) 
			throws ChainOfCommandViolationException {
		this(target, commandAgent, agentType);
		this.numberOfUnits = numberOfUnits;
	}

	@Override
	public void execute() {
		CommandAgent target = getTarget();
		List<Agent> subordinateAgents;
		if(agentType == null){
			subordinateAgents = getCommandedAgents();
		}
		else if(numberOfUnits == 0){
			if(idleOnly){
				subordinateAgents = getCommandedAgents(agentType, idleOnly);
			}
			else{
				subordinateAgents = getCommandedAgents(agentType);
			}
		}
		else{
			subordinateAgents = getCommandedAgents(agentType, numberOfUnits);
		}
		detachCommandedAgents(subordinateAgents, commandAgent);
	}
	
}
