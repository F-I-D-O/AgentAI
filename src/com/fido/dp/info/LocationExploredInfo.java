/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import bwapi.Position;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;

/**
 *
 * @author F.I.D.O.
 */
public class LocationExploredInfo extends Info{
	
	private final Position baseLocation;

	
	
	
	public Position getBaseLocation() {
		return baseLocation;
	}
	
	
	
	
	
	public LocationExploredInfo(CommandAgent recipient, Agent sender, Position baseLocation) {
		super(recipient, sender);
		this.baseLocation = baseLocation;
	}
	
}
