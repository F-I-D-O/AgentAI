/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo.activity.protoss;

import ninja.fido.agentAI.demo.activity.protoss.FormationTestSquadFormationIndividual;
import ninja.fido.agentAI.agent.FullCommander;
import ninja.fido.agentAI.agent.SquadCommander;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.base.CommandActivity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.ActivityGoal;
import ninja.fido.agentAI.base.UniversalGoalOrder;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

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
	protected void init() throws ChainOfCommandViolationException {
		squadCommander = new SquadCommander();
		GameAPI.addAgent(squadCommander, agent);
//		new UniversalGoalOrder(squadCommander, agent, 
//				new ActivityGoal(squadCommander, null, new FormationTestSquadFormation(squadCommander))).issueOrder();
		new UniversalGoalOrder(squadCommander, agent, new ActivityGoal(squadCommander, null, 
						new FormationTestSquadFormationIndividual(squadCommander))).issueOrder();
	}
	
}
