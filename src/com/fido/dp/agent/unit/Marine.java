/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.activity.AttackMove;
import com.fido.dp.activity.Wait;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.goal.AttackMoveGoal;
import com.fido.dp.goal.WaitGoal;

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
