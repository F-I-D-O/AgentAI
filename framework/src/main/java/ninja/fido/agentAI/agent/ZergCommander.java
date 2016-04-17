/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;


import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.OutbreakStrategyGoal;
import ninja.fido.agentAI.base.exception.CommanderNotCreatedException;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;

/**
 *
 * @author F.I.D.O.
 */
public class ZergCommander extends FullCommander{
	
	private static ZergCommander zergCommander;
	
	
	
	public static ZergCommander create(String name) throws MultipleCommandersException{
		return new ZergCommander(name);
	}
	
	public static ZergCommander get() throws CommanderNotCreatedException{
		if(zergCommander == null){
			throw new CommanderNotCreatedException(ZergCommander.class);
		}
		return zergCommander;
	}
	
	
	
	public LarvaCommand larvaCommand;
	
	public ExpansionCommand expansionCommand;
	
	protected ZergCommander(String name) throws MultipleCommandersException {
		super(name);
		zergCommander = this;
	}

	@Override
	protected Goal getDefaultGoal() {
		return new OutbreakStrategyGoal(this, null);
	}

	@Override
	protected void init() {
		super.init();
		
		larvaCommand = new LarvaCommand();
		GameAPI.addAgent(larvaCommand, this);
		
		expansionCommand = new ExpansionCommand();
		GameAPI.addAgent(expansionCommand, this);
	}
	
	
}
