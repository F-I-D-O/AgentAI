/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.activity;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.buildingPlacer.Building;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.goal.StartExpansionGoal;
import ninja.fido.agentSCAI.request.ResourceRequest;
import java.util.Objects;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansion extends Activity<Worker,StartExpansionGoal,StartExpansion>
		implements DecisionModuleActivity<Worker, StartExpansionGoal, StartExpansion>{
	
	private UnitType expansionBuildingType;
	
	private boolean unitOnExpansionSite;
	
	private Position expansionPosition;
	
	private boolean resourceRequested;

	
	
	
	public StartExpansion() {
	}

	public StartExpansion(Worker agent, UnitType expansionBuildingType, Position expansionPosition) {
		super(agent);
		this.expansionBuildingType = expansionBuildingType;
		this.expansionPosition = expansionPosition;
		unitOnExpansionSite = false;
		resourceRequested = false;
	}

	public StartExpansion(Worker agent, StartExpansionGoal goal) {
		super(agent);
		this.expansionBuildingType = goal.getExpansionBuildingType();
		this.expansionPosition = goal.getExpansionPosition();
	}
	
	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final StartExpansion other = (StartExpansion) obj;
		if (!Objects.equals(this.expansionBuildingType, other.expansionBuildingType)) {
			return false;
		}
		if (!Objects.equals(this.expansionPosition, other.expansionPosition)) {
			return false;
		}
		return true;
	}

	

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
		if(unitOnExpansionSite){
			if(agent.haveEnoughResourcersToBuild(expansionBuildingType)){
				TilePosition buildigPosition = GameAPI.getBuildingPlacer().getBuildingLocation(
					new Building(expansionPosition, expansionBuildingType, agent.getUnit(), false));
				buildigPosition = new TilePosition(36, 15);
				runChildActivity(new ConstructBuilding(agent, expansionBuildingType, buildigPosition));
			}
			else if(!resourceRequested){
				int missingMinerals = agent.getMissingMinerals(expansionBuildingType);
				int missingGas = agent.getMissingGas(expansionBuildingType);
				new ResourceRequest(agent.getCommandAgent(), agent, missingMinerals, missingGas, 0).send();
				resourceRequested = true;
			}
		}
		else{
			runChildActivity(new Move(agent, expansionPosition));
//			if(!unitOnMove && agent.getNumberOfFreeWorkers() > 0){
//				if((expansionPosition = agent.getNextExpansionPosition()) == null){
//					new ExpansionInfoRequest(FullCommander.get().explorationCommand, worker).send();
//				}
//				else if(worker == null){
//					worker = agent.getWorker();
//					new MoveOrder(worker, agent, expansionPosition).issueOrder();
//					worker.setAssigned(true);
//					unitOnMove = true;
//				}
//			}
		}
	}

	@Override
	protected void onChildActivityFinish(Activity activity) {
		super.onChildActivityFinish(activity);
		if(activity instanceof ConstructBuilding){
			agent.getGoal().setCompleted(resourceRequested);
		}
		else if(activity instanceof Move){
			unitOnExpansionSite = true;
		}
	}
	
	
	
	

	@Override
	protected void init() {
		
	}

	@Override
	public StartExpansion create(Worker agent, StartExpansionGoal goal) {
		return new StartExpansion(agent, goal);
	}
	
	
}
