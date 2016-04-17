/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.modules.decisionMaking;

import ninja.fido.agentAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class EmptyDecisionTableMapException extends Exception {
	private final Class<? extends Agent> agentClass;

	public EmptyDecisionTableMapException(Class<? extends Agent> agentClass) {
		this.agentClass = agentClass;
	}
	
	@Override
	public String getMessage() {
		return agentClass + ": Decision tables map is empty, cannot generate reference key!";
	}
}
