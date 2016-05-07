/* 
 * AgentAI
 */
package ninja.fido.agentAI.goal;

import bwapi.UnitType;
import ninja.fido.agentAI.agent.ExpansionCommand;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticExpansionGoal extends Goal{
	
	private final UnitType expansionBuildingType;

	
	
	public UnitType getExpansionBuildingType() {
		return expansionBuildingType;
	}
	
	
	
	

	public AutomaticExpansionGoal(ExpansionCommand agent, GoalOrder order, UnitType expansionBuildingType) {
		super(agent, order);
		this.expansionBuildingType = expansionBuildingType;
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
