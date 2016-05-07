/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public abstract class ArtificialWorker extends Worker{
	

	public ArtificialWorker(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	

}
