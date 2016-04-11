/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.base.exception;

import ninja.fido.agentai.base.Commander;

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
