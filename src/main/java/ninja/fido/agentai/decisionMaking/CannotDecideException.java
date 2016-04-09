/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.decisionMaking;

import ninja.fido.agentai.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class CannotDecideException extends Exception{
	
	private final Agent agent;
	
	private final DecisionTablesMapKey decisionTablesMapKey;

	
	
	
	public CannotDecideException(Agent agent, DecisionTablesMapKey decisionTablesMapKey) {
		this.agent = agent;
		this.decisionTablesMapKey = decisionTablesMapKey;
	}
	
	
	
	@Override
	public String getMessage() {
		return agent + ": Cannot decide what to do. Decision Table Key: " + decisionTablesMapKey;
	}
	
}
