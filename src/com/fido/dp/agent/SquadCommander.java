/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Position;
import com.fido.dp.DecisionMap;
import com.fido.dp.action.SquadAttackMove;
import com.fido.dp.action.Wait;
import com.fido.dp.base.Action;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.SquadAttackMoveGoal;
import com.fido.dp.goal.WaitGoal;
import java.util.HashMap;

/**
 *
 * @author F.I.D.O.
 */
public class SquadCommander extends CommandAgent {

	public SquadCommander() {
		reasoningOn = true;
		Goal goal = new SquadAttackMoveGoal(null, null, Position.None);
		HashMap<Double,Action> map = new HashMap<>();
		map.put(0.7, new SquadAttackMove(this, Position.None));
		addToReasoningMap(goal, new DecisionMap(goal, map));
	}
	
	

	@Override
	protected Action chooseAction() {
		if(getGoal() instanceof SquadAttackMoveGoal){
			SquadAttackMoveGoal goal = getGoal();
			return new SquadAttackMove(this, goal.getAttackTarget());
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
