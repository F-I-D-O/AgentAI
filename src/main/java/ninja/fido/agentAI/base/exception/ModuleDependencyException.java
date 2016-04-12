/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base.exception;

import ninja.fido.agentAI.base.GameApiModule;

/**
 *
 * @author F.I.D.O.
 */
public class ModuleDependencyException extends Exception{
	
	private final Class<? extends GameApiModule> moduleClass;
	
	private final Class<? extends GameApiModule> dependencyClass;
	
	
	

	public ModuleDependencyException(Class<? extends GameApiModule> moduleClass,
			Class<? extends GameApiModule> dependencyClass) {
		this.moduleClass = moduleClass;
		this.dependencyClass = dependencyClass;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return "Module " + moduleClass + " cannot be registered because unsatisfied dependency. You have to first "
				+ "register it's dependency module: " + dependencyClass;
	}
}
