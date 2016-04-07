/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import bwapi.Position;
import com.fido.dp.agent.unit.Marine;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
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
public class NormalSquadAttackMove extends SquadAttackMove
		implements StorableDecisionModuleActivity<SquadCommander, SquadAttackMoveGoal, NormalSquadAttackMove>{
	
	private static final int DEFAULT_MIN_SQUAD_SIZE = 5;
	
	private int minSquadSize;

	public NormalSquadAttackMove() {
	}

	public NormalSquadAttackMove(SquadCommander agent, SquadAttackMoveGoal goal) {
		super(agent, goal);
	}

	
	
	public NormalSquadAttackMove(SquadCommander agent, Position attackTarget) {
		super(agent, attackTarget);
	}
	
	
	@Override
	protected void performAction() {
		List<Marine> marines = agent.getCommandedAgents(Marine.class);
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
	
	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("normalSquadAttackMove");
		
		return parameter;
	}
	
	@Override
	public String getId() {
		return "normalSquadAttackMove";
	}

	@Override
	public NormalSquadAttackMove create(SquadCommander agent, SquadAttackMoveGoal goal) {
		return new NormalSquadAttackMove(agent, goal);
	}
}
