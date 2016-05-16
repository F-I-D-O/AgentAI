/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base.exception;

import ninja.fido.agentAI.base.GameAPIModule;

/**
 *
 * @author F.I.D.O.
 */
public class ModuleDependencyException extends Exception{
	
	private final Class<? extends GameAPIModule> moduleClass;
	
	private final Class<? extends GameAPIModule> dependencyClass;
	
	
	

	public ModuleDependencyException(Class<? extends GameAPIModule> moduleClass,
			Class<? extends GameAPIModule> dependencyClass) {
		this.moduleClass = moduleClass;
		this.dependencyClass = dependencyClass;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return "Module " + moduleClass + " cannot be registered because unsatisfied dependency. You have to first "
				+ "register it's dependency module: " + dependencyClass;
	}
}
