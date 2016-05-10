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
public abstract class ArtificialWorker<A extends ArtificialWorker> extends Worker<A>{

	public ArtificialWorker() throws EmptyDecisionTableMapException {
	}

	public ArtificialWorker(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	

}
