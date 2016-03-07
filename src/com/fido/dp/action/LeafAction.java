/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Unit;
import com.fido.dp.base.Agent;
import com.fido.dp.base.LeafAgent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class LeafAction extends Action{
	
	public LeafAction(Agent agent) {
		super(agent);
	}

	@Override
	public LeafAgent getAgent() {
		return (LeafAgent) agent;
	}
	
	
	
	public Unit getUnit() {
        return getAgent().getUnit();
    }
	
}
