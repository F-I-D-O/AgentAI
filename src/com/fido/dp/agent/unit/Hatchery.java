/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;

/**
 *
 * @author F.I.D.O.
 */
public class Hatchery extends UnitAgent{

	public Hatchery(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected Goal getDefaultGoal() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
