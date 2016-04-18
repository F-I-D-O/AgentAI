/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo.order;

import ninja.fido.agentAI.agent.BuildCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.demo.goal.BBSBuildGoal;

/**
 *
 * @author F.I.D.O.
 */
public class BBSBuildOrder extends GoalOrder<BuildCommand> {

	public BBSBuildOrder(BuildCommand target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new BBSBuildGoal(getTarget(), this));
	}
	
}
