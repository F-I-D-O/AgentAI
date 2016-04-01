/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.activity.Wait;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GameAgent;
import com.fido.dp.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Hatchery extends GameAgent{

	public Hatchery(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		return new Wait(this);
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
