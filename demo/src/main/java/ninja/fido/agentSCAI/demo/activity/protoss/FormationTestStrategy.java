/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.protoss;

import ninja.fido.agentSCAI.agent.FullCommander;
import ninja.fido.agentSCAI.agent.SquadCommander;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.UniversalGoalOrder;
import java.util.List;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;
import ninja.fido.agentSCAI.demo.goal.FormationTestSquadFormationGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class FormationTestStrategy extends CommandActivity<FullCommander,Goal,FormationTestStrategy>{
	
	private SquadCommander squadCommander;

	
	public FormationTestStrategy() {
	}

	public FormationTestStrategy(FullCommander agent) {
		super(agent);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof FormationTestStrategy;
	}

	@Override
	protected void performAction() {
		List<UnitAgent> agents = getCommandedAgents(UnitAgent.class);
		detachCommandedAgents(agents, squadCommander);
	}

	@Override
	protected void init() throws ChainOfCommandViolationException {
		try {
			squadCommander = new SquadCommander();
			GameAPI.addAgent(squadCommander, agent);
			new UniversalGoalOrder(squadCommander, agent,
					new FormationTestSquadFormationGoal(squadCommander, null)).issueOrder();
//		new UniversalGoalOrder(squadCommander, agent,
//				new FormationTestSquadFormationIndividualGoal(squadCommander, null)).issueOrder();
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public FormationTestStrategy create(FullCommander agent, Goal goal) {
		return new FormationTestStrategy(agent);
	}
	
}
