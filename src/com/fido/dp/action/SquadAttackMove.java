/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.agent.Marine;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.CommandAction;
import com.fido.dp.base.Goal;
import com.fido.dp.order.AttackMoveOrder;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMove extends CommandAction<SquadCommander>{
	
	protected final Position attackTarget;

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
	public void initialize(Goal goal) {
		
	}

	
	
	@Override
	protected void performAction() {
		List<Marine> marines = agent.getSubordinateAgents(Marine.class);
		Unit unit;
		for (Marine marine : marines) {
			if(marine.isIdle()){
				new AttackMoveOrder(marine, agent, attackTarget).issueOrder();
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
}
