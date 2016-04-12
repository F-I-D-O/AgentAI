/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.agent.ExplorationCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.StrategicExplorationGoal;

/**
 *
 * @author david
 */
public class StrategicExplorationOrder extends GoalOrder{

	public StrategicExplorationOrder(ExplorationCommand target, CommandAgent commandAgent) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new StrategicExplorationGoal(getTarget(), this));
	}
	
}
