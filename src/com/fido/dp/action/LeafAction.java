/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.base.Action;
import bwapi.Unit;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 */
public abstract class LeafAction<A extends UnitAgent, G extends Goal> extends Action<A,G>{

	public LeafAction(A agent) {
		super(agent);
	}
	
	
	public Unit getUnit() {
        return getAgent().getUnit();
    }
	
}
