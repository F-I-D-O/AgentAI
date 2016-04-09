/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.agent.ResourceCommand;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.goal.HarvestGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestOrder extends GoalOrder{
	
	private final double mineralShare;

	public HarvestOrder(ResourceCommand target, CommandAgent commandAgent, double mineralShare) {
		super(target, commandAgent);
		this.mineralShare = mineralShare;
	}

	@Override
	protected void execute() {
		setGoal(new HarvestGoal(getTarget(), this, mineralShare));
	}
	
}
