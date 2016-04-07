/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.agent.unit.Marine;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionStorage.StorableDecisionModuleActivity;
import com.fido.dp.goal.SquadAttackMoveGoal;
import com.fido.dp.order.AttackMoveOrder;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 */
public class ASAPSquadAttackMove extends SquadAttackMove
		implements StorableDecisionModuleActivity<SquadCommander, SquadAttackMoveGoal, ASAPSquadAttackMove>{

	public ASAPSquadAttackMove() {
	}

	public ASAPSquadAttackMove(SquadCommander agent, SquadAttackMoveGoal goal) {
		super(agent, goal);
	}
	
	
	
	public ASAPSquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent, attackTarget);
	}
	
	@Override
	protected void performAction() {
		List<Marine> marines = agent.getCommandedAgents(Marine.class);
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
	
	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("ASAPSquadAttackMove");
		
		return parameter;
	}

	@Override
	public String getId() {
		return "ASAPSquadAttackMove";
	}

	@Override
	public ASAPSquadAttackMove create(SquadCommander agent, SquadAttackMoveGoal goal) {
		return new ASAPSquadAttackMove(agent, goal);
	}
	
	
}
