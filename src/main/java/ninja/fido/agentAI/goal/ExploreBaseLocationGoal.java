/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.goal;

import bwapi.Position;
import ninja.fido.agentAI.Scout;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

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
