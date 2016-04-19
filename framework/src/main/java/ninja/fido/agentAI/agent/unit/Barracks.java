/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.activity.AutomaticProduction;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.goal.AutomaticProductionGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
	
	
	
	
	
	

	public Barracks(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
//		trainingInProgress = false;
	}

	@Override
	protected Activity chooseActivity() {
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
	
	public boolean isSupplyMissing() {
		return getMissingSupply() > 0;
	}
	
	public int getMissingMinerals(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.mineralPrice() - getOwnedMinerals();
	}
	
	public int getMissingSupply(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.supplyRequired() - getOwnedSupply();
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
