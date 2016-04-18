/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.DroneProductionGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
