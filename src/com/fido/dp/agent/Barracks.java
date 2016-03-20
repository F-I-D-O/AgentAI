/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.base.Action;
import com.fido.dp.action.AutomaticProduction;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.goal.AutomaticProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Barracks extends UnitAgent{
	
//	private boolean trainingInProgress;
//
//	
//	
//	public boolean isTrainingInProgress() {
//		return trainingInProgress;
//	}
	
	private UnitType automaticProductionUnitType;

	public void setAutomaticProductionUnitType(UnitType automaticProductionUnitType) {
		this.automaticProductionUnitType = automaticProductionUnitType;
	}
	
	
	
	
	
	

	public Barracks(Unit unit) {
		super(unit);
//		trainingInProgress = false;
	}

	@Override
	protected Action chooseAction() {
		if(getGoal() instanceof AutomaticProductionGoal){
			return new AutomaticProduction(this, UnitType.Terran_Marine);
		}
		return null;
	}
	
	public void train(UnitType unitType){
		unit.train(unitType);
//		trainingInProgress = true;
	}
	
	public void onTrainingFinished(){
//		trainingInProgress = false;
	}
	
	public boolean isMineralsMissing() {
		return getMissingMinerals() > 0;
	}
	
	public int getMissingMinerals(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.mineralPrice() - getOwnedMinerals();
	}

	public void automaticProduction() {
		if(!unit.isTraining() && automaticProductionUnitType.mineralPrice() <= getOwnedMinerals()){
			train(automaticProductionUnitType);
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return new AutomaticProductionGoal(this, null);
	}
	
}
