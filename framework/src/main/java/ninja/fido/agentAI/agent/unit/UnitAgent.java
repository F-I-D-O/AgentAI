/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public abstract class UnitAgent extends GameAgent{

	public UnitAgent() throws EmptyDecisionTableMapException {
	}
	
	public UnitAgent(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	public void move(Position position){
		GameAPI.move(this, position);
	}
}
