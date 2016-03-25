/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Position;
import com.fido.dp.DecisionMap;
import com.fido.dp.action.ASAPSquadAttackMove;
import com.fido.dp.action.NormalSquadAttackMove;
import com.fido.dp.action.Wait;
import com.fido.dp.base.Action;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.SquadAttackMoveGoal;
import com.fido.dp.goal.WaitGoal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class SquadCommander extends CommandAgent {

	public SquadCommander() {
		reasoningOn = true;
		
		SquadAttackMoveGoal squadAttackMoveGoal = new SquadAttackMoveGoal(null, null, Position.None);
		TreeMap<Double,Action> actionMap = new TreeMap<>();
		actionMap.put(0.2, new ASAPSquadAttackMove(this, Position.None));
		actionMap.put(1.0, new NormalSquadAttackMove(this, Position.None));
		addToReasoningMap(squadAttackMoveGoal.getClass(), new DecisionMap(squadAttackMoveGoal, actionMap));
		
		WaitGoal waitGoal = new WaitGoal(null, null);
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Wait(this));
		addToReasoningMap(waitGoal.getClass(), new DecisionMap(waitGoal, actionMap));
	}
	
	

	@Override
	protected Action chooseAction() {
		if(getGoal() instanceof SquadAttackMoveGoal){
			SquadAttackMoveGoal goal = getGoal();
			return new ASAPSquadAttackMove(this, goal.getAttackTarget());
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
