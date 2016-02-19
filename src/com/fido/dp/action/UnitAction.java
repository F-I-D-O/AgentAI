/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.Agent;
import com.fido.dp.agent.LeafAgent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class UnitAction extends Action {

	public LeafAgent getUnitAgent() {
		return (LeafAgent) agent;
	}

	public UnitAction(LeafAgent unitAgent) {
		super(unitAgent);
	}
	
	
}
