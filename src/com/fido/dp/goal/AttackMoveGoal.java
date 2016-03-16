/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.Position;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;

/**
 *
 * @author F.I.D.O.
 */
public class AttackMoveGoal extends Goal{
	
	private final Position attackTarget;
	
	public AttackMoveGoal(UnitAgent agent, Position attackTarget) {
		super(agent);
		this.attackTarget = attackTarget;
	}

	public Position getAttackTarget() {
		return attackTarget;
	}
	
}
