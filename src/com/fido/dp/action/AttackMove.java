/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import com.fido.dp.base.Action;
import com.fido.dp.base.UnitAgent;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMove extends Action<UnitAgent>{
	
	private final Position target;

	public AttackMove(UnitAgent agent, Position target) {
		super(agent);
		this.target = target;
	}

	@Override
	protected void performAction() {
		
	}

	@Override
	protected void init() {
		agent.getUnit().attack(target);
	}
	
}
