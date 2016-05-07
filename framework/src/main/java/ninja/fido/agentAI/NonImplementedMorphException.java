/* 
 * AgentAI
 */
package ninja.fido.agentAI;

import bwapi.UnitType;

/**
 *
 * @author F.I.D.O.
 */
public class NonImplementedMorphException extends Exception{
	
	private final MorphableUnit formerUnitAgent;
	
	private final UnitType unitType;

	
	
	public NonImplementedMorphException(MorphableUnit formerUnitAgent, UnitType unitType) {
		this.formerUnitAgent = formerUnitAgent;
		this.unitType = unitType;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return formerUnitAgent + ": morph to " + unitType + " is not implemented";
	}
}
