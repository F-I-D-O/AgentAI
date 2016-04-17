/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import ninja.fido.agentAI.activity.AttackMove;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.AttackMoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Marine extends UnitAgent{

	public Marine(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseActivity() {
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
