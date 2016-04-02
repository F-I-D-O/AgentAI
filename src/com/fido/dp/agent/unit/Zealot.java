/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent.unit;

import bwapi.Unit;
import com.fido.dp.activity.Move;
import com.fido.dp.activity.Wait;
import com.fido.dp.activity.protoss.GroupGuard;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.GroupGuardGoal;
import com.fido.dp.goal.MoveGoal;
import com.fido.dp.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Zealot extends UnitAgent{

	public Zealot(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseAction() {
		if(getGoal() instanceof MoveGoal){
			MoveGoal goal = getGoal();
			if(goal.getMinDistanceFromTarget() == MoveGoal.DEFAULT_MIN_DISTANCE_FROM_TARGET){
				return new Move(this, goal.getTargetPosition());
			}
			else{
				return new Move(this, goal.getTargetPosition(), goal.getMinDistanceFromTarget());
			}
		}
		else if(getGoal() instanceof WaitGoal){
			return new Wait(this);
		}
		else if(getGoal() instanceof GroupGuardGoal){
			GroupGuardGoal groupGuardGoal = getGoal();
			return new GroupGuard(this, groupGuardGoal.getVip(), groupGuardGoal.getGuards());
		}
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
