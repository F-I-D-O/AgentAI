/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.TilePosition;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class InvalidTilePositionException extends Exception {
	
	private final Agent agent;
	
	private final TilePosition targetTilePosition;

	
	
	
	public InvalidTilePositionException(Agent agent, TilePosition targetTilePosition) {
		this.agent = agent;
		this.targetTilePosition = targetTilePosition;
	}
	
	
	
	@Override
	public String getMessage() {
		return agent + ": cannot build on here. Position: " + targetTilePosition;
	}
	
}
