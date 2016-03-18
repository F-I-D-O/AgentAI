/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.base.UnitAgent;
import bwapi.Unit;
import com.fido.dp.base.Action;
import com.fido.dp.base.Goal;

/**
 *
 * @author david_000
 */
public class Vulture extends UnitAgent {

    public Vulture(Unit unit) {
        super(unit);
    }

    @Override
    protected Action chooseAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	protected Goal getDefaultGoal() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
    
}
