/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.terran;

import bwapi.Position;
import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.agent.unit.Marine;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.agent.UnitCommand;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.order.SquadAttackMoveOrder;
import java.util.List;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;

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
	protected void performAction() throws ChainOfCommandViolationException {
		List<Marine> marines = agent.getCommandedAgents(Marine.class);
		agent.detachCommandedAgents(marines, squadCommander);
		
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
	}
	
}
