/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent;

import bwapi.Position;
import bwapi.Unit;

/**
 *
 * @author david_000
 */
public interface Scout {
    
//    public void commandExploreBaseLocation(Position targetBase);
	
	public Unit getUnit();
	
	public Position getPosition();
    
}
