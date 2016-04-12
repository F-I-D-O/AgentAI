/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

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
