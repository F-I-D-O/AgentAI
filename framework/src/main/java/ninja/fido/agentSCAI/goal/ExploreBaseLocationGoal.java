/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.Position;
import ninja.fido.agentSCAI.agent.Scout;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class ExploreBaseLocationGoal extends Goal {
	
	private final Position baseLocation;
	
	private boolean locationExplored;

	
	
	public Position getBaseLocation() {
		return baseLocation;
	}
	
	
	
	public ExploreBaseLocationGoal(Scout agent, GoalOrder order, Position baseLocation) {
		super((Agent) agent, order);
		this.baseLocation = baseLocation;
		locationExplored = false;
	}

	@Override
	public boolean isCompleted() {
		return locationExplored;
	}
	
	public void setLocationExplored(Position baseLocation){
		if(this.baseLocation.equals(baseLocation)){
			locationExplored = true;
		}
	}
}
