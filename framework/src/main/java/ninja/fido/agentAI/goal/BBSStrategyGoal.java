/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.goal;

import ninja.fido.agentAI.base.Commander;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSStrategyGoal extends Goal{
	
	public BBSStrategyGoal(Commander agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}