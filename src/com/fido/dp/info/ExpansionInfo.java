package com.fido.dp.info;


import bwapi.Position;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Info;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfo extends Info{
	
	private final Position expansionPosition;

	public Position getExpansionPosition() {
		return expansionPosition;
	}
	
	
	
	public ExpansionInfo(Agent recipient, ExplorationCommand sender, Position expansionPosition) {
		super(recipient, sender);
		this.expansionPosition = expansionPosition;
	}
	
}
