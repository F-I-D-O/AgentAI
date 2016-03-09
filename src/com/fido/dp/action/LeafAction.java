/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Unit;
import com.fido.dp.base.LeafAgent;

/**
 *
 * @author F.I.D.O.
 * @param <T>
 */
public abstract class LeafAction<T extends LeafAgent> extends Action<T>{

	public LeafAction(T agent) {
		super(agent);
	}
	
	
	public Unit getUnit() {
        return getAgent().getUnit();
    }
	
}
