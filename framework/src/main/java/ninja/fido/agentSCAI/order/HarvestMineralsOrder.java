/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.agent.unit.SCV;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.HarvestMineralsGoal;

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
