/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base.exception;

import ninja.fido.agentSCAI.base.Commander;

/**
 *
 * @author F.I.D.O.
 */
public class CommanderNotCreatedException extends Exception{
	
	private final Class<? extends Commander> commanderClass;

	public CommanderNotCreatedException(Class<? extends Commander> commanderClass) {
		this.commanderClass = commanderClass;
	}

	
	@Override
	public String getMessage() {
		return commanderClass + ": commander has not been created yet.";
	}
}
