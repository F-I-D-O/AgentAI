/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base;

import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public abstract class GoalOrder<T extends Agent> extends Order<T> {

	public GoalOrder(T target, CommandAgent commandAgent) throws ChainOfCommandViolationException {
		super(target, commandAgent);
	}
	
	protected final void setGoal(Goal goal){
		Agent target = getTarget();
		target.setGoal(goal);
	}
}
