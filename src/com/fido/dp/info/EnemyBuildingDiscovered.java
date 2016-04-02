/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import com.fido.dp.base.Info;
import bwapi.Unit;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBuildingDiscovered extends Info {
	private final Unit building;

	
	
	
	public Unit getBuilding() {
		return building;
	}
	
	
	

	public EnemyBuildingDiscovered(CommandAgent recipient, GameAgent sender,Unit building) {
		super(recipient, sender);
		this.building = building;
	}
}
