/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Activity;
import com.fido.dp.goal.StartExpansionGoal;
import com.fido.dp.request.ResourceRequest;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class StartExpansion extends Activity<Worker, StartExpansionGoal>{
	
	private UnitType expansionBuildingType;
	
	private boolean unitOnExpansionSite;
	
	private TilePosition buildigPosition;
	
	private boolean resourceRequested;

	public StartExpansion(Worker agent, UnitType expansionBuildingType, TilePosition buildigPosition) {
		super(agent);
		this.expansionBuildingType = expansionBuildingType;
		this.buildigPosition = buildigPosition;
		unitOnExpansionSite = false;
		resourceRequested = false;
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
		if (!Objects.equals(this.buildigPosition, other.buildigPosition)) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(StartExpansionGoal goal) {
		this.expansionBuildingType = goal.getExpansionBuildingType();
		this.buildigPosition = goal.getBuildigPosition();
	}

	

	@Override
	protected void performAction() {
		if(unitOnExpansionSite){
			if(agent.haveEnoughResourcersToBuild(expansionBuildingType)){
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
			runChildActivity(new Move(agent, buildigPosition.toPosition()));
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
	
	
}
