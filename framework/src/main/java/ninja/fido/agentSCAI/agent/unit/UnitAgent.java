/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.GameAgent;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 * @param <A> Agent type;
 */
public abstract class UnitAgent<A extends UnitAgent> extends GameAgent<A>{

	public UnitAgent() throws EmptyDecisionTableMapException {
	}
	
	public UnitAgent(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	public void move(Position position){
		GameAPI.move(this, position);
	}
}
