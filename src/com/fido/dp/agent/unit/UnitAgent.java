/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class UnitAgent extends GameAgent{
	
	public UnitAgent(Unit unit) {
		super(unit);
	}
	
	public void move(Position position){
		GameAPI.move(this, position);
	}
}
