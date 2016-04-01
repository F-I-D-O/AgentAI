/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import com.fido.dp.base.GameAgent;
import bwapi.Unit;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;

/**
 *
 * @author david_000
 */
public class Vulture extends GameAgent {

    public Vulture(Unit unit) {
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
