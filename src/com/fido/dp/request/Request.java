/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.base;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Request {
	
	private final CommandAgent recipient;
	
	private final Agent sender;

	
	
	
	public Agent getSender() {
		return sender;
	}
	
	

	public Request(CommandAgent recipient, Agent sender) {
		this.recipient = recipient;
		this.sender = sender;
	}
	
	public final void send(){
		recipient.queRequest(this);
		sender.addSendedRequest(this);
	}
}
