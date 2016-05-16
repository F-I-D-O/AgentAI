/* 
 * AgentAI - Demo
 */
package ninja.fido.agentSCAI.demo.order;

import ninja.fido.agentAI.agent.BuildCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.demo.goal.BBSBuildGoal;

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
