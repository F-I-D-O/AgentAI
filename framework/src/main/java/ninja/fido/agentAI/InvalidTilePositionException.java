/* 
 * AgentAI
 */
package ninja.fido.agentAI;

import bwapi.TilePosition;
import ninja.fido.agentAI.base.Agent;

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
