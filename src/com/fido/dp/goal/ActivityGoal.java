/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.base.GoalOrder;

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
