/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import ninja.fido.agentAI.base.GoalOrder;
import bwapi.Position;
import bwapi.UnitType;
import ninja.fido.agentAI.agent.unit.Worker;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.StartExpansionGoal;

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
