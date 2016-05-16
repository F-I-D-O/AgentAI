/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;


import java.util.logging.Level;
import java.util.logging.Logger;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.exception.MultipleCommandersException;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
	protected void onInitialize() {
		try {
			super.onInitialize();
			
			larvaCommand = new LarvaCommand();
			GameAPI.addAgent(larvaCommand, this);
			
			expansionCommand = new ExpansionCommand();
			GameAPI.addAgent(expansionCommand, this);
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}
	
	
}
