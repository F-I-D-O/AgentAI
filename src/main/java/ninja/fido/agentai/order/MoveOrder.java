/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.order;

import bwapi.Position;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GoalOrder;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentai.goal.MoveGoal;

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
