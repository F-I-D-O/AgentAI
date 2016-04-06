/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.activity.ASAPSquadAttackMove;
import com.fido.dp.activity.Wait;
import com.fido.dp.base.Activity;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.ActivityGoal;
import com.fido.dp.goal.SquadAttackMoveGoal;
import com.fido.dp.goal.WaitGoal;

/**
 *
 * @author F.I.D.O.
 */
public class SquadCommander extends CommandAgent {

	public SquadCommander() {
		
//		TreeMap<Double,Activity> actionMap = new TreeMap<>();
//		actionMap.put(0.2, new ASAPSquadAttackMove(this, Position.None));
//		actionMap.put(1.0, new NormalSquadAttackMove(this, Position.None));
//		DecisionTablesMapKey key =  new DecisionTablesMapKey();
//		key.addParameter(new GoalParameter(SquadAttackMoveGoal.class));
//		addToDecisionTablesMap(key, new DecisionTable(actionMap));
//		
//		actionMap = new TreeMap<>();
//		actionMap.put(1.0, new Wait(this));
//		key =  new DecisionTablesMapKey();
//		key.addParameter(new GoalParameter(WaitGoal.class));
//		addToDecisionTablesMap(key, new DecisionTable(actionMap));
		
//		referenceKey = key;
	}
	
	

	@Override
	protected Activity chooseAction() {
		if(getGoal() instanceof SquadAttackMoveGoal){
			SquadAttackMoveGoal goal = getGoal();
			return new ASAPSquadAttackMove(this, goal.getAttackTarget());
		}
		else if(getGoal() instanceof WaitGoal){
			return new Wait(this);
		}
		
		// to remove later
		else if(getGoal() instanceof ActivityGoal){
			ActivityGoal activityGoal = getGoal();
			return activityGoal.getActivity();
		}
		return null;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
}
