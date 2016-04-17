/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class HighTemplar extends UnitAgent{

	public HighTemplar(Unit unit) {
		super(unit);
	}

	@Override
	protected Activity chooseActivity() {
		if(getGoal() instanceof WaitGoal){
			return new Wait(this);
		}
		else if(getGoal() instanceof MoveGoal){
			MoveGoal goal = getGoal();
			if(goal.getMinDistanceFromTarget() == MoveGoal.DEFAULT_MIN_DISTANCE_FROM_TARGET){
				return new Move(this, goal.getTargetPosition());
			}
			else{
				return new Move(this, goal.getTargetPosition(), goal.getMinDistanceFromTarget());
			}
		}
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
