/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.protoss;

import com.fido.dp.agent.FullCommander;
import com.fido.dp.agent.SquadCommander;
import com.fido.dp.agent.unit.UnitAgent;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.ActivityGoal;
import com.fido.dp.base.UniversalGoalOrder;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class FormationTestStrategy extends CommandActivity<FullCommander, Goal>{
	
	private SquadCommander squadCommander;

	public FormationTestStrategy(FullCommander agent) {
		super(agent);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof FormationTestStrategy;
	}

	@Override
	protected void performAction() {
		List<UnitAgent> agents = agent.getCommandedAgents(UnitAgent.class);
		agent.detachCommandedAgents(agents, squadCommander);
	}

	@Override
	protected void init() {
		squadCommander = new SquadCommander();
		GameAPI.addAgent(squadCommander, agent);
//		new UniversalGoalOrder(squadCommander, agent, 
//				new ActivityGoal(squadCommander, null, new FormationTestSquadFormation(squadCommander))).issueOrder();
		new UniversalGoalOrder(squadCommander, agent, new ActivityGoal(squadCommander, null, 
						new FormationTestSquadFormationIndividual(squadCommander))).issueOrder();
	}
	
}
