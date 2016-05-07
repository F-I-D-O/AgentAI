/* 
 * AgentAI
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.agent.ExplorationCommand;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.StrategicExplorationGoal;

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
