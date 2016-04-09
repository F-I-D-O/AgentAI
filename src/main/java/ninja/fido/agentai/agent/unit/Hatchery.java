/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Hatchery extends GameAgent{

	public Hatchery(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		return new Wait(this);
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
