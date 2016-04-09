/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import bwapi.TilePosition;
import ninja.fido.agentai.base.Agent;

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
