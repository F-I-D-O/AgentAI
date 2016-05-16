/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.info;

import bwapi.UnitType;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Request;

/**
 *
 * @author F.I.D.O.
 */
public class UnitCreationStartedInfo extends Request{
	
	private final UnitType unitType;

	
	
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	
	
	public UnitCreationStartedInfo(CommandAgent recipient, Agent sender, UnitType unitType) {
		super(recipient, sender);
		this.unitType = unitType;
	}
	
}
