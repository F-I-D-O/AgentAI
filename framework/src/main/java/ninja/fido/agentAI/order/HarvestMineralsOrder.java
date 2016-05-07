/* 
 * AgentAI
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.agent.unit.SCV;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsOrder extends GoalOrder<SCV>{

	public HarvestMineralsOrder(SCV target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		setGoal(new HarvestMineralsGoal(getTarget(), this));
	}
	
}
