/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.order;

import ninja.fido.agentSCAI.agent.BuildCommand;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
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
