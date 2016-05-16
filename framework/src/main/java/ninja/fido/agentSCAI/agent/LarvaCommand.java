/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;

import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.DroneProductionGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class LarvaCommand extends CommandAgent{

	public LarvaCommand() throws EmptyDecisionTableMapException{
		
	}
	

	@Override
	protected Goal getDefaultGoal() {
		return new DroneProductionGoal(this, null);
	}
	
}
