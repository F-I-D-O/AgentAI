/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.Position;
import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansionGoal extends Goal{
	
	private final UnitType expansionBuildingType;

	private final Position expansionPosition;
	
	
	
	public UnitType getExpansionBuildingType() {
		return expansionBuildingType;
	}

	public Position getExpansionPosition() {
		return expansionPosition;
	}
	
	
	

	public StartExpansionGoal(Worker agent, GoalOrder order, UnitType expansionBuilding, Position expansionPosition) {
		super(agent, order);
		this.expansionPosition = expansionPosition;
		this.expansionBuildingType = expansionBuilding;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}
	
}
