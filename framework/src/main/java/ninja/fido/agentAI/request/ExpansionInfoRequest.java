/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.request;

import ninja.fido.agentAI.base.Request;
import ninja.fido.agentAI.agent.ExplorationCommand;
import ninja.fido.agentAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfoRequest extends Request{
	
	public ExpansionInfoRequest(ExplorationCommand recipient, Agent sender) {
		super(recipient, sender);
	}
	
}
