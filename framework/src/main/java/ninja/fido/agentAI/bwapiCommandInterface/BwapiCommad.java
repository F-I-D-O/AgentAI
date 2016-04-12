/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.bwapiCommandInterface;

import ninja.fido.agentAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
abstract class BwapiCommad<A extends GameAgent> {
	private final A agent;

	public A getAgent() {
		return agent;
	}
	
	
	

	public BwapiCommad(A agent) {
		this.agent = agent;
	}
	
	
	
	public abstract String getType();
}
