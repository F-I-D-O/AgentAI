/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.HarvestMineralsGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Probe extends Worker{

	public Probe(Unit unit) {
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
	
}
