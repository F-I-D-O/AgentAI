/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import com.fido.dp.agent.Marine;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.order.AttackMoveOrder;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class NormalSquadAttackMove extends SquadAttackMove{
	
	private static final int DEFAULT_MIN_SQUAD_SIZE = 10;
	
	private int minSquadSize;

	public NormalSquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent, attackTarget);
	}
	
	
	@Override
	protected void performAction() {
		List<Marine> marines = agent.getSubordinateAgents(Marine.class);
		if(marines.size() >= minSquadSize){
			for (Marine marine : marines) {
				if(marine.isIdle()){
					new AttackMoveOrder(marine, agent, attackTarget).issueOrder();
				}
			}
		}
	}

	@Override
	protected void init() {
		minSquadSize = DEFAULT_MIN_SQUAD_SIZE;
	}
}
