/* 
 * AgentAI
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import bwapi.UnitType;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.activity.AutomaticProduction;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.goal.AutomaticProductionGoal;
import ninja.fido.agentAI.goal.WaitGoal;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class Barracks extends GameAgent<Barracks>{
	
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

	
	
	
	public Barracks() throws EmptyDecisionTableMapException {
	}

	public Barracks(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());
		defaultActivityMap.put(AutomaticProductionGoal.class, new AutomaticProduction(UnitType.Terran_Marine));

		return defaultActivityMap;
	}
	
	public void train(UnitType unitType) throws ResourceDeficiencyException{
		spendResource(ResourceType.MINERALS, unitType.mineralPrice());
		spendResource(ResourceType.GAS, unitType.gasPrice());
		spendResource(ResourceType.SUPPLY, unitType.supplyRequired());
		GameAPI.train(this, unitType);
	}
	
	public void onTrainingFinished(){
	}
	
	public boolean isMineralsMissing() {
		return getMissingMinerals() > 0;
	}
	
	public boolean isGasMissing() {
		return getMissingGas() > 0;
	}
	
	public boolean isSupplyMissing() {
		return getMissingSupply() > 0;
	}
	
	public int getMissingMinerals(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.mineralPrice() - getOwnedMinerals();
	}
	
	public int getMissingGas(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.gasPrice() - getOwnedGas();
	}
	
	public int getMissingSupply(){
		return automaticProductionUnitType == null ? 0 : automaticProductionUnitType.supplyRequired() - getOwnedSupply();
	}

	public void automaticProduction() throws ResourceDeficiencyException {
		if(!unit.isTraining()){
			train(automaticProductionUnitType);
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return new AutomaticProductionGoal(this, null);
	}

	@Override
	public Barracks create(Unit unit) throws EmptyDecisionTableMapException {
		return new Barracks(unit);
	}
	
}
