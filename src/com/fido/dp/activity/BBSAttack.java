/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import com.fido.dp.BaseLocationInfo;
import com.fido.dp.agent.Marine;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.agent.UnitCommand;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.order.SquadAttackMoveOrder;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class BBSAttack extends CommandActivity<UnitCommand,Goal>{
	
	private SquadCommander squadCommander;
	
	private Position enemyBaseLocation;
	
	private boolean attackOrdered;

	public BBSAttack(UnitCommand agent) {
		super(agent);
		attackOrdered = false;
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
		final BBSAttack other = (BBSAttack) obj;
		return true;
	}
	
	

	@Override
	protected void performAction() {
		List<Marine> marines = agent.getSubordinateAgents(Marine.class);
		agent.detachSubordinateAgents(marines, squadCommander);
		
		for (BaseLocationInfo baseInfo : agent.getEnemyBases()) {
			enemyBaseLocation = baseInfo.getPosition();
			break;
		}
		
		if(!attackOrdered && enemyBaseLocation != null){
			new SquadAttackMoveOrder(squadCommander, agent, enemyBaseLocation).issueOrder();
			attackOrdered = true;
		}
	}

	@Override
	protected void init() {
		squadCommander = new SquadCommander();
		GameAPI.addAgent(squadCommander, agent);
//		agent.addSubordinateAgent(squadCommander);
	}
	
}
