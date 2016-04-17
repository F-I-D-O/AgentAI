/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.activity;

import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentAI.buildingPlacer.Building;
import ninja.fido.agentAI.agent.unit.Worker;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.UnitActivity;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.goal.ConstructBuildingGoal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuilding extends UnitActivity<Worker,Goal,ConstructBuilding>
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
		super(agent);
		ConstructBuildingGoal constructBuildingGoal = (ConstructBuildingGoal) goal;
		buildingType = constructBuildingGoal.getBuildingType();
		placeToBuildOn = constructBuildingGoal.getPlaceToBuildOn();
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
