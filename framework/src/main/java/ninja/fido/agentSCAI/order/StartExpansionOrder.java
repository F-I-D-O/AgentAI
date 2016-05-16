/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.order;

import ninja.fido.agentSCAI.base.GoalOrder;
import bwapi.Position;
import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.goal.StartExpansionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansionOrder extends GoalOrder<Worker>{
	
	private final UnitType expansionBuildingType;

//	private final TilePosition buildigPosition;
	
	private final Position expansionPosition;

//	public StartExpansionOrder(Worker target, CommandAgent commandAgent, UnitType expansionBuilding, 
//			TilePosition buildigPosition) {
//		super(target, commandAgent);
//		this.buildigPosition = buildigPosition;
//		this.expansionBuildingType = expansionBuilding;
//	}
	
	public StartExpansionOrder(Worker target, CommandAgent commandAgent, UnitType expansionBuilding, 
			Position expansionPosition) throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.expansionPosition = expansionPosition;
		this.expansionBuildingType = expansionBuilding;
	}

	@Override
	protected void execute() {
//		setGoal(new StartExpansionGoal(getTarget(), this, expansionBuildingType, buildigPosition));
		setGoal(new StartExpansionGoal(getTarget(), this, expansionBuildingType, expansionPosition));
	}
	
}
