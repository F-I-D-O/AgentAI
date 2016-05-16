/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI;

import bwapi.Position;
import bwta.BaseLocation;

/**
 * Base location info.
 * @author F.I.D.O.
 */
public class BaseLocationInfo {
	
	/**
	 * BWAPI base location.
	 */
	private final BaseLocation baseLocation;
	
	/**
	 * Determines if the base location has been explored.
	 */
	private boolean explored;
	
	/**
	 * Determines if some agent is on the waz to explore this base location.
	 */
	private boolean explorationInProgress;
	
	/**
	 * Determines if the base location is base controlled by this bot.
	 */
	private final boolean isOurBase;
	
	/**
	 * Determines if the base location is controlled by the enemy.
	 */
	private boolean isEnemyBase;
	
	/**
	 * Determines if the base has been chosen for expansion.
	 */
	private boolean chosenForExpansion;

	
	
	
	/**
	 * Returns true if the base has been explored.
	 * @return Returns true if the base has been explored.
	 */
	public boolean isExpplored() {
		return explored;
	}

	/**
	 * Sets the explored flag.
	 * @param explored True for explored, false otherwise.
	 */
	public void setExpplored(boolean explored) {
		this.explored = explored;
	}

	/**
	 * Returns true if this base is control by the bot.
	 * @return Returns true if this base is control by the bot.
	 */
	public boolean isOurBase() {
		return isOurBase;
	}

	/**
	 * Returns true if the base is controlled by the enemy.
	 * @return Returns true if the base is controlled by the enemy.
	 */
	public boolean isIsEnemyBase() {
		return isEnemyBase;
	}

	/**
	 * Sets the enemy base flag.
	 * @param isEnemyBase Tru for enemy base, false otherwise.
	 */
	public void setIsEnemyBase(boolean isEnemyBase) {
		this.isEnemyBase = isEnemyBase;
	}

	/**
	 * Returns true if some agent is on the way to explore this location.
	 * @return Returns true if some agent is on the way to explore this location.
	 */
	public boolean isExplorationInProgress() {
		return explorationInProgress;
	}

	/**
	 * Sets the exploration in progress flag. True for in progress.
	 * @param explorationInProgress 
	 */
	public void setExplorationInProgress(boolean explorationInProgress) {
		this.explorationInProgress = explorationInProgress;
	}

	/**
	 * Returns true if the base has been chosen for expansion.
	 * @return Returns true if the base has been chosen for expansion.
	 */
	public boolean isChosenForExpansion() {
		return chosenForExpansion;
	}

	/**
	 * Set the chosen for expansion mark. True for chosen.
	 * @param chosenForExpansion 
	 */
	public void setChosenForExpansion(boolean chosenForExpansion) {
		this.chosenForExpansion = chosenForExpansion;
	}
	
	
	
	
	
	/**
	 * Constructor.
	 * @param baseLocation BWAPI base location.
	 * @param isOurBase True if this is your base.
	 */
	public BaseLocationInfo(BaseLocation baseLocation, boolean isOurBase) {
		this.baseLocation = baseLocation;
		this.isOurBase = isOurBase;
		explored = false;
		chosenForExpansion = false;
	}
	
	
	
	
	/**
	 * Returns base position.
	 * @return Returns base position.
	 */
	public Position getPosition(){
		return baseLocation.getPosition();
	}
	
	/**
	 * Returns tru if this is zour start location.
	 * @return Returns tru if this is zour start location.
	 */
	public boolean isStartLocation(){
		return baseLocation.isStartLocation();
	}
}
