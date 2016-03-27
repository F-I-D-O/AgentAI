/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import bwapi.UnitType;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Request;

/**
 *
 * @author F.I.D.O.
 */
public class UnitCreationStartedInfo extends Request{
	
	private final UnitType unitType;

	
	
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	
	
	public UnitCreationStartedInfo(CommandAgent recipient, Agent sender, UnitType unitType) {
		super(recipient, sender);
		this.unitType = unitType;
	}
	
}
