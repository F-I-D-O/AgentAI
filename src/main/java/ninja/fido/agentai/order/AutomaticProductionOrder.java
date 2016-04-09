/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import ninja.fido.agentai.agent.unit.Barracks;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentai.goal.AutomaticProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticProductionOrder extends GoalOrder{

	public AutomaticProductionOrder(Barracks target, CommandAgent commandAgent)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new AutomaticProductionGoal(getTarget(), this));
	}
	
}
