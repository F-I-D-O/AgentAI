/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import bwapi.Position;
import ninja.fido.agentAI.agent.Scout;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.ExploreBaseLocationGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ExploreBaseLocationOrder extends GoalOrder{
	
	private final Position baseLocation;

	public Position getBaseLocation() {
		return baseLocation;
	}
	
	

	public ExploreBaseLocationOrder(Scout target, CommandAgent commandAgent, Position baseLocation) 
			throws ChainOfCommandViolationException {
		super((Agent) target, commandAgent);
		this.baseLocation = baseLocation;
	}

	@Override
	protected void execute() {
		setGoal(new ExploreBaseLocationGoal(getTarget(), this, baseLocation));
	}	
	
	
}
