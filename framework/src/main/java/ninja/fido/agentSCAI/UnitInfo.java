/* 
 * AgentAI
 */
package ninja.fido.agentSCAI;

import bwapi.Position;
import bwapi.Unit;

/**
 *
 * @author F.I.D.O.
 */
public class UnitInfo {
	private Unit unit;
	
	private Position position;

	public Position getPosition() {
		return position;
	}
	
	
	
	

	public UnitInfo(Unit unit, Position position) {
		this.unit = unit;
		this.position = position;
	}
	
	
}
