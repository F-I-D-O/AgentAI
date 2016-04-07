/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.UnitType;
import com.fido.dp.agent.ExpansionCommand;
import com.fido.dp.agent.FullCommander;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Order;
import com.fido.dp.decisionMaking.DecisionModuleActivity;
import com.fido.dp.goal.AutomaticExpansionGoal;
import com.fido.dp.request.ExpansionInfoRequest;

/**
 *
 * @author F.I.D.O.
 */
public class AutomaticExpansion extends CommandActivity<ExpansionCommand, AutomaticExpansionGoal> 
		implements DecisionModuleActivity<ExpansionCommand, AutomaticExpansionGoal, AutomaticExpansion>{
	
	private UnitType expansionBuildingType;
	
	private boolean expansionRequestSended;

	
	
	
	public AutomaticExpansion() {
	}

	public AutomaticExpansion(ExpansionCommand agent, UnitType expansionBuilding) {
		super(agent);
		this.expansionBuildingType = expansionBuilding;
		expansionRequestSended = false;
	}

	public AutomaticExpansion(ExpansionCommand agent, AutomaticExpansionGoal goal) {
		super(agent, goal);
		this.expansionBuildingType = goal.getExpansionBuildingType();
		expansionRequestSended = false;
	}
	
	
	

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof AutomaticExpansion;
	}

	@Override
	protected void performAction() {
		Worker worker;
		if(agent.getNextExpansionPosition() == null && !expansionRequestSended){
			new ExpansionInfoRequest(FullCommander.get().explorationCommand, agent).send();
			expansionRequestSended = true;
		}
		else if(agent.getNextExpansionPosition() != null && (worker = agent.getWorker()) != null){
			agent.startExpansion(expansionBuildingType, worker);
			expansionRequestSended = false;
		}
	}

	@Override
	protected void init() {
		
	}

	@Override
	public void initialize(ExpansionCommand agent, AutomaticExpansionGoal goal) {
		super.initialize(agent, goal);
		expansionBuildingType = goal.getExpansionBuildingType();
	}
	
	

	@Override
	protected void handleCompletedOrder(Order order) {
		
	}

	@Override
	public AutomaticExpansion create(ExpansionCommand agent, AutomaticExpansionGoal goal) {
		return new AutomaticExpansion(agent, goal);
	}
	
	
}
