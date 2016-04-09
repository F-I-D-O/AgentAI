/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.goal;

import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestMineralsGoal extends Goal{
	
	public HarvestMineralsGoal(Worker agent, GoalOrder order) {
		super(agent, order);
	}

	@Override
	public boolean isCompleted() {
		return false;
	}
	
}
