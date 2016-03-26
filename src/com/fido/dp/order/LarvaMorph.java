/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.agent.unit.Larva;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaMorph extends GoalOrder{
	
	private final Larva.MorphOption morphOption;

	public LarvaMorph(Larva target, CommandAgent commandAgent, Larva.MorphOption morphOption) {
		super(target, commandAgent);
		this.morphOption = morphOption;
	}

	@Override
	protected void execute() {
		Larva larva = getTarget();
		larva.Morph(morphOption);
	}
	
}
