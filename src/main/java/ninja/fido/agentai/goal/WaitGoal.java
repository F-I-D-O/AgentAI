/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.goal;

import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class WaitGoal extends Goal{
	
	public WaitGoal(Agent agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}