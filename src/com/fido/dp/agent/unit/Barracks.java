/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import bwapi.UnitCommand;
import bwapi.UnitType;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.base.Activity;
import com.fido.dp.activity.AutomaticProduction;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GameAgent;
import com.fido.dp.goal.AutomaticProductionGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Barracks extends GameAgent{
	
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
	protected Activity chooseAction() {
		if(getGoal() instanceof AutomaticProductionGoal){
			return new AutomaticProduction(this, UnitType.Terran_Marine);
		}
		return null;
	}
	
	public void train(UnitType unitType) throws ResourceDeficiencyException{
		spendSupply(ResourceType.MINERALS, unitType.mineralPrice());
		spendSupply(ResourceType.GAS, unitType.gasPrice());
		spendSupply(ResourceType.SUPPLY, unitType.supplyRequired());
		GameAPI.train(this, unitType);
	}
	
	public void onTrainingFinished(){
	}
	
	public boolean isMineralsMissing() {
		return getMissingMinerals() > 0;
	}
	
	public int getMissingMinerals(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.mineralPrice() - getOwnedMinerals();
	}

	public void automaticProduction() throws ResourceDeficiencyException {
		if(!unit.isTraining() && automaticProductionUnitType.mineralPrice() <= getOwnedMinerals()){
			train(automaticProductionUnitType);
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return new AutomaticProductionGoal(this, null);
	}
	
}
