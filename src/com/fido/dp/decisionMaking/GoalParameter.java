/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 */
public class GoalParameter extends DecisionTablesMapParametr<Agent,Class<? extends Goal>,GoalParameter>{

	public GoalParameter(Class<? extends Goal> value) {
		super(value);
	}

	@Override
	public GoalParameter getCurrentParameter(Agent agent) {
		return new GoalParameter(agent.getGoal().getClass());
	}

	
	
}
