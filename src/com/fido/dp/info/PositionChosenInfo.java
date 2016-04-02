/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import com.fido.dp.base.Info;
import bwapi.Position;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class PositionChosenInfo extends Info{
	private final Position chosenPosition;

	public Position getChosenPosition() {
		return chosenPosition;
	}
	
	

	public PositionChosenInfo(Agent recipient, Agent sender, Position chosenPosition) {
		super(recipient, sender);
		this.chosenPosition = chosenPosition;
	}
}
