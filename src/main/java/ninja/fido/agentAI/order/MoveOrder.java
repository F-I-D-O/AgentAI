/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.order;

import bwapi.Position;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.GoalOrder;
import ninja.fido.agentAI.base.GameAgent;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentAI.goal.MoveGoal;

/**
 *
 * @author F.I.D.O.
 */
public class MoveOrder extends GoalOrder{
	
	private final Position targetPosition;

	public MoveOrder(GameAgent target, CommandAgent commandAgent, Position targetPosition)
			throws ChainOfCommandViolationException {
		super(target, commandAgent);
		this.targetPosition = targetPosition;
	}

	@Override
	protected void execute() {
		setGoal(new MoveGoal(getTarget(), this, targetPosition));
	}
	
}
