/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.agent.ResourceCommand;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.HarvestGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestOrder extends GoalOrder<ResourceCommand>{
	
	private final double mineralShare;

	public HarvestOrder(ResourceCommand target, CommandAgent commandAgent, double mineralShare) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.mineralShare = mineralShare;
	}

	@Override
	protected void execute() {
		setGoal(new HarvestGoal(getTarget(), this, mineralShare));
	}
	
}
