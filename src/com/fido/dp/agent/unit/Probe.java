/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Probe extends Worker{

	public Probe(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}
	
}
