/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.goal.SquadAttackMoveGoal;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public abstract class SquadAttackMove extends CommandActivity<SquadCommander,SquadAttackMoveGoal>{
	
	protected Position attackTarget;

	public SquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent);
		this.attackTarget = attackTarget;
	}

	
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SquadAttackMove other = (SquadAttackMove) obj;
		if (!Objects.equals(this.attackTarget, other.attackTarget)) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(SquadCommander agent, SquadAttackMoveGoal goal) {
		super.initialize(agent, goal);
		this.attackTarget = goal.getAttackTarget();
	}

	
	
}
