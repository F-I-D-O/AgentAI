/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.protoss;

import ninja.fido.agentai.agent.FullCommander;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.agent.unit.UnitAgent;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.ActivityGoal;
import ninja.fido.agentai.base.UniversalGoalOrder;
import java.util.List;
import ninja.fido.agentai.base.exception.ChainOfCommandViolationException;

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
