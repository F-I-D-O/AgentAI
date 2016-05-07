/* 
 * AgentAI
 */
package ninja.fido.agentAI.mapTools;

import bwapi.Position;
import bwapi.TilePosition;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public interface MapTools {
	
	public ArrayList<TilePosition> getClosestTilesTo(Position position);
	
	public TilePosition getNextExpansion();
}
