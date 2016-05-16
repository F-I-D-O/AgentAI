/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base;

import java.util.List;

/**
 * GameAPI module interface.
 * @author F.I.D.O.
 */
public interface GameAPIModule {
	
	/**
	 * Called before connecting to StarCraft.
	 */
	public void onRun();

	/**
	 * Called on game start.
	 * @param winner True if game was victory, false otherwise.
	 * @param score Game score.
	 */
	public void onEnd(boolean winner, int score);

	/**
	 * Called on game start.
	 * @param gameCount Current game count. Starts from 1.
	 */
	public void onStart(int gameCount);
	
	/**
	 * Returns list of modules this module depends on.
	 * @return 
	 */
	public List<Class<? extends GameAPIModule>> getDependencies();
}
