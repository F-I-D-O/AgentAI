/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;

import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.fido.agentSCAI.base.Commander;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.exception.MultipleCommandersException;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class FullCommander extends Commander{
	
	public ExplorationCommand explorationCommand;
	
	public ResourceCommand resourceCommand;

	public BuildCommand buildCommand;


	
	
	
	private ExplorationCommand getExplorationCommand() {
		return explorationCommand;
	}

	
	
	
	public FullCommander(String name, Goal initialGoal) throws MultipleCommandersException, 
			EmptyDecisionTableMapException {
		super(name, initialGoal);
	}

	
	
	@Override
	protected FullCommander create() throws MultipleCommandersException, EmptyDecisionTableMapException{
		return new FullCommander(name,initialGoal);
	}

	@Override
	protected void onInitialize() {
		try {
			explorationCommand = new ExplorationCommand();
			GameAPI.addAgent(explorationCommand, this);
			resourceCommand = new ResourceCommand();
			GameAPI.addAgent(resourceCommand, this);
			buildCommand = new BuildCommand();
			GameAPI.addAgent(buildCommand, this);
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}
	
}
