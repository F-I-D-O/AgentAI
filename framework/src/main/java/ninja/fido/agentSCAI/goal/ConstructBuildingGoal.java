/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.goal;

import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingGoal extends Goal {
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;
	
	private boolean buildingConstructionFinished;

	
	
	public UnitType getBuildingType() {
		return buildingType;
	}

	public TilePosition getPlaceToBuildOn() {
		return placeToBuildOn;
	}

	public void setBuildingConstructionFinished(boolean buildingConstructionFinished) {
		this.buildingConstructionFinished = buildingConstructionFinished;
	}

	
	
	
	public ConstructBuildingGoal(Worker agent, GoalOrder order, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent, order);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	public boolean isCompleted() {
		return buildingConstructionFinished;
	}
	
}
