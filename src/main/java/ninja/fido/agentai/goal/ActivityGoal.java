/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.goal;

import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GoalOrder;

/**
 *
 * @author F.I.D.O.
 */
public class ActivityGoal extends Goal{
	
	private final Activity activity;

	public Activity getActivity() {
		return activity;
	}
	
	

	public ActivityGoal(Agent agent, GoalOrder order, Activity activity) {
		super(agent, order);
		this.activity = activity;
	}

	@Override
	public boolean isCompleted() {
		return completed;
	}
	
}
