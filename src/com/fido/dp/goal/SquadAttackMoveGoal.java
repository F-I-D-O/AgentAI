/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.Position;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMoveGoal extends Goal{
	
	private final Position attackTarget;
	
	public SquadAttackMoveGoal(SquadCommander agent, Position attackTarget) {
		super(agent);
		this.attackTarget = attackTarget;
	}

	public Position getAttackTarget() {
		return attackTarget;
	}
	
}
