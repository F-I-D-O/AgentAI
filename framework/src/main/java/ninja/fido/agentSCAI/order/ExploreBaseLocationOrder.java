/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.Position;
import ninja.fido.agentAI.agent.Scout;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.ExploreBaseLocationGoal;

/**
 *
 * @author F.I.D.O.
 * @param <T>
 */
public class ExploreBaseLocationOrder<T extends Agent & Scout> extends GoalOrder<T>{
	
	private final Position baseLocation;

	public Position getBaseLocation() {
		return baseLocation;
	}
	
	

	public ExploreBaseLocationOrder(T target, CommandAgent commandAgent, Position baseLocation) 
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.baseLocation = baseLocation;
	}

	@Override
	protected void execute() {
		setGoal(new ExploreBaseLocationGoal(getTarget(), this, baseLocation));
	}	
	
	
}
