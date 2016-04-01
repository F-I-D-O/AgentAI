/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.Position;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GoalOrder;
import com.fido.dp.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class MoveGoal extends Goal {
	
	public static final int MIN_DISTANCE_FROM_TARGET = 200;
	
	
	
	private final Position targetPosition;

	
	
	
	public Position getTargetPosition() {
		return targetPosition;
	}
	
	
	

	public MoveGoal(GameAgent target, GoalOrder order, Position targetPosition) {
		super(target, order);
		this.targetPosition = targetPosition;
	}

	@Override
	public boolean isCompleted() {
		return ((GameAgent) agent).getUnit().getPosition().getDistance(targetPosition) <= MIN_DISTANCE_FROM_TARGET;
	}
	
}
