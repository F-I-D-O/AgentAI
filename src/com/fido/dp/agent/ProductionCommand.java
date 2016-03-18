/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.base.Action;
import com.fido.dp.action.BBSProduction;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.BBSProductionGoal;
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
	protected Action chooseAction() {
		if(getGoal() instanceof BBSProductionGoal){
			return new BBSProduction(this);
		}
		return null;
	}

	public boolean isMineralsMissing() {
		return getMissingMinerals() > 0;
	}

	public int getMissingMinerals() {
		int missingCrystal = 0;
		for (Barracks barracksTmp : barracks) {
			missingCrystal += barracksTmp.getMissingMinerals();
		}
		return missingCrystal - getOwnedMinerals();
	}

	@Override
	protected void onSubordinateAgentAdded(Agent subordinateAgent) {
		super.onSubordinateAgentAdded(subordinateAgent); 
		if(subordinateAgent instanceof Barracks){
			barracks.add((Barracks) subordinateAgent);
		}	
	}

	@Override
	protected Goal getDefaultGoal() {
		return new BBSProductionGoal(this);
	}
	
	
	
}
