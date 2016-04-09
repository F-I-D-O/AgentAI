/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity;

import bwapi.Position;
import ninja.fido.agentai.agent.unit.Marine;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.decisionStorage.StorableDecisionModuleActivity;
import ninja.fido.agentai.goal.SquadAttackMoveGoal;
import ninja.fido.agentai.order.AttackMoveOrder;
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
