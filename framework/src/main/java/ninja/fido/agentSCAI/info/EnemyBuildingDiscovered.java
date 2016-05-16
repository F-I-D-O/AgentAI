/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.info;

import ninja.fido.agentSCAI.base.Info;
import bwapi.Unit;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBuildingDiscovered extends Info {
	private final Unit building;

	
	
	
	public Unit getBuilding() {
		return building;
	}
	
	
	

	public EnemyBuildingDiscovered(CommandAgent recipient, GameAgent sender,Unit building) {
		super(recipient, sender);
		this.building = building;
	}
}
