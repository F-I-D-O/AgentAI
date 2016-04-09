/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Unit;
import ninja.fido.agentai.activity.Move;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.activity.protoss.GroupGuard;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.GroupGuardGoal;
import ninja.fido.agentai.goal.MoveGoal;
import ninja.fido.agentai.goal.WaitGoal;

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
