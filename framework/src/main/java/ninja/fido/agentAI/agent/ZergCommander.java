/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;


import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 */
public class ZergCommander extends FullCommander{
	
	public LarvaCommand larvaCommand;
	
	public ExpansionCommand expansionCommand;
	
	public ZergCommander(String name, Goal initialGoal) throws MultipleCommandersException, 
			EmptyDecisionTableMapException {
		super(name,initialGoal);
	}
	
	
	
	
	@Override
	protected ZergCommander create() throws MultipleCommandersException, EmptyDecisionTableMapException{
		return new ZergCommander(name, initialGoal);
	}
	

	@Override
	protected void init() {
		try {
			super.init();
			
			larvaCommand = new LarvaCommand();
			GameAPI.addAgent(larvaCommand, this);
			
			expansionCommand = new ExpansionCommand();
			GameAPI.addAgent(expansionCommand, this);
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}
	
	
}
