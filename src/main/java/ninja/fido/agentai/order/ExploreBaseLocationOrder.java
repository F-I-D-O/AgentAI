/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import bwapi.Position;
import ninja.fido.agentai.Scout;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentai.goal.ExploreBaseLocationGoal;

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
