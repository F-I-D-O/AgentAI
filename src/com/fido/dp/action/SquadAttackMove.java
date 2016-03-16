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
import com.fido.dp.order.AttackMoveOrder;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class SquadAttackMove extends CommandAction<SquadCommander>{
	
	private final Position attackTarget;

	public SquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent);
		this.attackTarget = attackTarget;
	}

	@Override
	protected void performAction() {
		List<Marine> marines = agent.getSubordinateAgents(Marine.class);
		Unit unit;
		for (Marine marine : marines) {
			if(marine.isIdle()){
				new AttackMoveOrder(marine, agent, attackTarget).issueCommand();
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
}
