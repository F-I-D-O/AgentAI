/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.ConstructBuildingGoal;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuildingOrder extends GoalOrder<Worker>{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuildingOrder(Worker target, CommandAgent commandAgent, UnitType buildingType, 
			TilePosition placeToBuildOn) throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	protected void execute() {
		setGoal(new ConstructBuildingGoal(getTarget(), this, buildingType, placeToBuildOn));
	}
	
}
