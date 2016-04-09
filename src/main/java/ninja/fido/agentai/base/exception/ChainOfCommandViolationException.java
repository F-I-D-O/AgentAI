/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.base.exception;

import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Order;

/**
 *
 * @author F.I.D.O.
 */
public class ChainOfCommandViolationException extends Exception{
	private final Agent violator;
	
	private final Order order;
	
	private final Agent target;

	public ChainOfCommandViolationException(Agent violator, Order order, Agent target) {
		this.violator = violator;
		this.order = order;
		this.target = target;
	}
	
	
	
	@Override
	public String getMessage() {
		return "Chain of command has been violated by " + violator.getClass() + ". " + target.getClass()
				+ " is not under it|s command (order: " + order + ").";
	}
}
