/*
 * AgentAI
 */
package ninja.fido.agentAI;

import bwapi.UnitType;

/**
 *
 * @author F.I.D.O.
 */
public class NoGameAgentForUnitException extends Exception{
	
	private final UnitType unitType;

	
	
	public NoGameAgentForUnitException(UnitType unitType) {
		this.unitType = unitType;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return "No game agent registered for unit: " + unitType;
	}
	
}
