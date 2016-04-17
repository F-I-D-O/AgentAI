/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Probe extends Worker{

	public Probe(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseActivity() {
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestMineralsGoal(this, null);
	}
	
}
