/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.Log;
import com.fido.dp.MorphableUnit;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.HarvestMineralsGoal;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class Drone extends Worker implements MorphableUnit{

	public Drone(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}

//	public void morph(UnitType buildingType) {
//		Log.log(this, Level.INFO, "{0}: morphing into: {1}", this.getClass(), buildingType.getClass());
//		
//		if(GameAPI.getGame().canBuildHere(unit.getTilePosition(), buildingType)){
//			unit.morph(buildingType);
//			constructionProcessInProgress = true;
//			constructedBuildingType = buildingType;
//		}
//		else{
//			Log.log(this, Level.SEVERE, "{0}: cannot build here! position: {1}, building: {2}", this.getClass(), 
//					unit.getTilePosition(), buildingType);
//		}
//		
//	}

	@Override
	public void onMorphFinished() {
		Log.log(this, Level.INFO, "{0}: morph into {1} finished.", this.getClass(), unit.getType());
	}

	
}
