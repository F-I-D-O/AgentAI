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
import com.fido.dp.order.AttackMoveOrder;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class ASAPSquadAttackMove extends SquadAttackMove{
	
	public ASAPSquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent, attackTarget);
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
}
