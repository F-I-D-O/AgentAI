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
 * @param <T>
 */
public class ExploreBaseLocationOrder<T extends Agent & Scout> extends GoalOrder<T>{
	
	private final Position baseLocation;

	public Position getBaseLocation() {
		return baseLocation;
	}
	
	

	public ExploreBaseLocationOrder(T target, CommandAgent commandAgent, Position baseLocation) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.baseLocation = baseLocation;
	}

	@Override
	protected void execute() {
		setGoal(new ExploreBaseLocationGoal(getTarget(), this, baseLocation));
	}	
	
	
}
