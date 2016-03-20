/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.base.Action;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class Wait extends Action<Agent>{

	public Wait(Agent agent) {
		super(agent);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof Wait;
	}
	
	
	
	

	@Override
	protected void performAction() {
		
	}

	@Override
	protected void init() {
		
	}

	
	
	
	
}
