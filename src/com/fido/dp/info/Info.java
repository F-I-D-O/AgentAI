/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Info {
	private final Agent recipient;
	
	private final Agent sender;

	public Info(Agent recipient, Agent sender) {
		this.recipient = recipient;
		this.sender = sender;
	}
	
	public final void send(){
		recipient.queInfo(this);
	}
}
