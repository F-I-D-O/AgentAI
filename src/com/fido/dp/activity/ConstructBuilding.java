/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.Building;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitActivity;
import com.fido.dp.decisionMaking.DecisionModuleActivity;
import com.fido.dp.goal.ConstructBuildingGoal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuilding extends UnitActivity<Worker,Goal>
		implements DecisionModuleActivity<Worker, Goal, ConstructBuilding>{
	
	private UnitType buildingType;
	
	private TilePosition placeToBuildOn;

	
	
	
	public ConstructBuilding() {
	}

	public ConstructBuilding(Worker agent, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	public ConstructBuilding(Worker agent, Goal goal) {
		super(agent, goal);
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
		final ConstructBuilding other = (ConstructBuilding) obj;
		if (!Objects.equals(this.buildingType, other.buildingType)) {
			return false;
		}
		if (!Objects.equals(this.placeToBuildOn, other.placeToBuildOn)) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(Worker agent, Goal goal) {
		super.initialize(agent, goal);
		ConstructBuildingGoal constructBuildingGoal = (ConstructBuildingGoal) goal;
		buildingType = constructBuildingGoal.getBuildingType();
		placeToBuildOn = constructBuildingGoal.getPlaceToBuildOn();
	}
	
	
	
	

	@Override
	protected void init() {
		agent.build(buildingType, placeToBuildOn);
	}

	@Override
	protected void performAction() {
		
//		 if position is invalid, find nearest position
		if(agent.isInvalidBuildPositionFailure()){
			placeToBuildOn = GameAPI.getBuildingPlacer().getBuildingLocation(
				new Building(placeToBuildOn.toPosition(), buildingType, agent.getUnit(), false));
			agent.build(buildingType, placeToBuildOn);
		}
	}

	@Override
	protected void onChildActivityFinish(Activity activity) {
		super.onChildActivityFinish(activity);
	}

	@Override
	public ConstructBuilding create(Worker agent, Goal goal) {
		return new ConstructBuilding(agent, goal);
	}
	
	
	
	
	
}
