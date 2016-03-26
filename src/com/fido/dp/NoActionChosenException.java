/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;

/**
 *
 * @author david
 */
public class NoActionChosenException extends Exception{
	
	private final Class<? extends Agent> agentClass;

	private final Goal goal;
	
	private final Class<? extends Agent> commandAgentClass;
	
	public NoActionChosenException(Class<? extends Agent> agentClass, Goal goal, Class<? extends Agent> commandAgentClass) {
		this.agentClass = agentClass;
		this.goal = goal;
		this.commandAgentClass = commandAgentClass;
	}

	@Override
	public String getMessage() {
		return "No action has been chosen! Agent class: " + agentClass + ", goal: " + goal + ", Command Agent class: "
				+ commandAgentClass;
	}

}
