/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.base.Order;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DetachBack extends Order{

	private Class agentType = null;
	
	private int numberOfUnits = 0;
	
	private boolean idleOnly = false;

	
	
	public Class getAgentType() {
		return agentType;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}
	
	
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType) {
		super(target, commandAgent);
		this.agentType = agentType;
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType, boolean idleOnly) {
		this(target, commandAgent, agentType);
		this.idleOnly = idleOnly;
	}
	
	public DetachBack(CommandAgent target, CommandAgent commandAgent, Class agentType, int numberOfUnits) {
		this(target, commandAgent, agentType);
		this.numberOfUnits = numberOfUnits;
	}

	@Override
	public void execute() {
		CommandAgent target = getTarget();
		List<Agent> subordinateAgents;
		if(agentType == null){
			subordinateAgents = target.getSubordinateAgents();
		}
		else if(numberOfUnits == 0){
			if(idleOnly){
				subordinateAgents = target.getSubordinateAgents(agentType, idleOnly);
			}
			else{
				subordinateAgents = target.getSubordinateAgents(agentType);
			}
		}
		else{
			subordinateAgents = target.getSubordinateAgents(agentType, numberOfUnits);
		}
		target.detachSubordinateAgents(subordinateAgents, commandAgent);
	}
	
}
