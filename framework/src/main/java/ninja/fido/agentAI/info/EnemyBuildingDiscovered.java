/* 
 * AgentAI
 */
package ninja.fido.agentAI.info;

import ninja.fido.agentAI.base.Info;
import bwapi.Unit;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GameAgent;

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
