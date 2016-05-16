/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.agent.ExplorationCommand;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.StrategicExplorationGoal;

/**
 *
 * @author david
 */
public class StrategicExplorationOrder extends GoalOrder<ExplorationCommand>{

	public StrategicExplorationOrder(ExplorationCommand target, CommandAgent commandAgent) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new StrategicExplorationGoal(getTarget(), this));
	}
	
}
