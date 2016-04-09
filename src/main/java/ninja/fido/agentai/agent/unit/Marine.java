/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import ninja.fido.agentai.activity.AttackMove;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.AttackMoveGoal;
import ninja.fido.agentai.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Marine extends UnitAgent{

	public Marine(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		if(getGoal() instanceof AttackMoveGoal){
			AttackMoveGoal goal = getGoal();
			return new AttackMove(this, goal.getAttackTarget());
		}
		if(getGoal() instanceof WaitGoal){
			return new Wait(this);
		}
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
