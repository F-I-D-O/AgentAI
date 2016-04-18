/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.fido.agentAI.base.Commander;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
	protected void init() {
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
