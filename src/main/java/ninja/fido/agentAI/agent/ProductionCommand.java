/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.agent.unit.Barracks;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.activity.terran.BBSProduction;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.BBSProductionGoal;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class ProductionCommand extends CommandAgent{
	
	private final ArrayList<Barracks> barracks;

	
	
	public ArrayList<Barracks> getBarracks() {
		return barracks;
	}

	

	public ProductionCommand() {
		this.barracks = new ArrayList<>();
	}
	

	@Override
	protected Activity chooseAction() {
		if(getGoal() instanceof BBSProductionGoal){
			return new BBSProduction(this);
		}
		return null;
	}

	public boolean isMineralsMissing() {
		return getMissingMinerals() > 0;
	}
	
	public boolean isSupplyMissing() {
		return getMissingSupply() > 0;
	}

	public int getMissingMinerals() {
		int missingCrystal = 0;
		for (Barracks barracksTmp : barracks) {
			missingCrystal += barracksTmp.getMissingMinerals();
		}
		return missingCrystal - getOwnedMinerals();
	}
	
	public int getMissingSupply() {
		int missingSupply = 0;
		for (Barracks barracksTmp : barracks) {
			missingSupply += barracksTmp.getMissingSupply();
		}
		return missingSupply - getOwnedSupply();
	}

	@Override
	protected void onCommandedAgentAdded(Agent subordinateAgent) {
		super.onCommandedAgentAdded(subordinateAgent); 
		if(subordinateAgent instanceof Barracks){
			barracks.add((Barracks) subordinateAgent);
		}	
	}

	@Override
	protected Goal getDefaultGoal() {
		return new BBSProductionGoal(this, null);
	}
	
	
	
}
