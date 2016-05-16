/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.info;

import ninja.fido.agentSCAI.base.Info;
import bwapi.Position;
import ninja.fido.agentSCAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class PositionChosenInfo extends Info{
	private final Position chosenPosition;

	public Position getChosenPosition() {
		return chosenPosition;
	}
	
	

	public PositionChosenInfo(Agent recipient, Agent sender, Position chosenPosition) {
		super(recipient, sender);
		this.chosenPosition = chosenPosition;
	}
}
