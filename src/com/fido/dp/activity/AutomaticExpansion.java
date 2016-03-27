/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.ExpansionCommand;
import com.fido.dp.agent.FullCommander;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Order;
import com.fido.dp.goal.AutomaticExpansionGoal;
import com.fido.dp.order.StartExpansionOrder;
import com.fido.dp.request.ExpansionInfoRequest;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticExpansion extends CommandActivity<ExpansionCommand, AutomaticExpansionGoal>{
	
	private UnitType expansionBuildingType;
	
	private boolean expansionRequestSend;

	public AutomaticExpansion(ExpansionCommand agent, UnitType expansionBuilding) {
		super(agent);
		this.expansionBuildingType = expansionBuilding;
		expansionRequestSend = false;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof AutomaticExpansion;
	}

	@Override
	protected void performAction() {
		Worker worker;
		if(agent.getNextExpansionPosition() == null && !expansionRequestSend){
			new ExpansionInfoRequest(FullCommander.get().explorationCommand, agent).send();
		}
		else if((worker = agent.getWorker()) != null){
			TilePosition buildingPosition = agent.findPositionForBuild(expansionBuildingType, worker);
			new StartExpansionOrder(worker, agent, expansionBuildingType, buildingPosition).issueOrder();
		}
	}

	@Override
	protected void init() {
		
	}

	@Override
	public void initialize(AutomaticExpansionGoal goal) {
		expansionBuildingType = goal.getExpansionBuildingType();
	}
	
	

	@Override
	protected void handleCompletedOrder(Order order) {
		
	}
	
	
}
