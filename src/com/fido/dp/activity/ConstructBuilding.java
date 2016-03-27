/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.ArtificialWorker;
import com.fido.dp.agent.unit.Drone;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitActivity;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class ConstructBuilding extends UnitActivity<Worker,Goal>{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	public ConstructBuilding(Worker agent, UnitType buildingType, TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
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
//		if(agent instanceof ArtificialWorker){
			agent.build(buildingType, placeToBuildOn);
//		}
//		// for zerg
//		else{
//			runChildActivity(new Move(agent, placeToBuildOn.toPosition()));
//		}
	}

	@Override
	protected void performAction() {
		
	}

	@Override
	protected void onChildActivityFinish(Activity activity) {
		super.onChildActivityFinish(activity);
//		((Drone) agent).morph(buildingType);
	}
	
	
	
	
	
}
