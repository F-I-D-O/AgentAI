/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.agent.unit.SCV;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentai.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsOrder extends GoalOrder{

	public HarvestMineralsOrder(SCV target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new HarvestMineralsGoal(getTarget(), this));
	}
	
}
