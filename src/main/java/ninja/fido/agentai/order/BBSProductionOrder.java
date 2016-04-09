/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import ninja.fido.agentai.agent.ProductionCommand;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.goal.BBSProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class BBSProductionOrder extends GoalOrder {

	public BBSProductionOrder(ProductionCommand target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new BBSProductionGoal(getTarget(), this));
	}
	
}
