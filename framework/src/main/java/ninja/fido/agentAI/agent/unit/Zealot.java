/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent.unit;

import bwapi.Unit;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.Move;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.GroupGuardGoal;
import ninja.fido.agentAI.goal.MoveGoal;
import ninja.fido.agentAI.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class Zealot extends UnitAgent{

	public Zealot(Unit unit) {
		super(unit);
	}

	

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}

	@Override
	protected Map<Class<? extends Goal>, ? extends Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();
//		if(getGoal() instanceof MoveGoal){
//			MoveGoal goal = getGoal();
//			if(goal.getMinDistanceFromTarget() == MoveGoal.DEFAULT_MIN_DISTANCE_FROM_TARGET){
//				return new Move(this, goal.getTargetPosition());
//			}
//			else{
//				return new Move(this, goal.getTargetPosition(), goal.getMinDistanceFromTarget());
//			}
//		}
		defaultActivityMap.put(WaitGoal.class, new Wait());
		defaultActivityMap.put(GroupGuardGoal.class, new GroupGuard());
//		else if(getGoal() instanceof GroupGuardGoal){
//			GroupGuardGoal groupGuardGoal = getGoal();
//			return new GroupGuard(this, groupGuardGoal.getVip(), groupGuardGoal.getGuards());
//		}
		return defaultActivityMap;
	}
	
	
	
}
