/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.command;

import com.fido.dp.base.Command;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DeatchBack extends Command{

	private Class agentType = null;
	
	private int numberOfUnits = 0;

	
	
	public Class getAgentType() {
		return agentType;
	}

	public int getNumberOfUnits() {
		return numberOfUnits;
	}
	
	
	
	public DeatchBack(CommandAgent target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}
	
	public DeatchBack(CommandAgent target, CommandAgent commandAgent, Class agentType) {
		super(target, commandAgent);
		this.agentType = agentType;
	}
	
	public DeatchBack(CommandAgent target, CommandAgent commandAgent, Class agentType, int numberOfUnits) {
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
			subordinateAgents = target.getSubordinateAgents(agentType);
		}
		else{
			subordinateAgents = target.getSubordinateAgents(agentType, numberOfUnits);
		}
		target.detachSubordinateAgents(subordinateAgents, commandAgent);
	}
	
}
