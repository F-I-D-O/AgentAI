/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import bwapi.Position;
import bwta.BaseLocation;

/**
 *
 * @author F.I.D.O.
 */
public class BaseLocationInfo {
	
	private final BaseLocation baseLocation;
	
	private boolean explored;
	
	private boolean explorationInProgress;
	
	private final boolean isOurBase;
	
	private boolean isEnemyBase;
	
	private boolean chosenForExpansion;

	
	
	public boolean isExpplored() {
		return explored;
	}

	public void setExpplored(boolean expplored) {
		this.explored = expplored;
	}

	public boolean isOurBase() {
		return isOurBase;
	}

	public boolean isIsEnemyBase() {
		return isEnemyBase;
	}

	public void setIsEnemyBase(boolean isEnemyBase) {
		this.isEnemyBase = isEnemyBase;
	}

	public boolean isExplorationInProgress() {
		return explorationInProgress;
	}

	public void setExplorationInProgress(boolean explorationInProgress) {
		this.explorationInProgress = explorationInProgress;
	}

	public boolean isChosenForExpansion() {
		return chosenForExpansion;
	}

	public void setChosenForExpansion(boolean chosenForExpansion) {
		this.chosenForExpansion = chosenForExpansion;
	}
	
	
	
	
	

	public BaseLocationInfo(BaseLocation baseLocation, boolean isOurBase) {
		this.baseLocation = baseLocation;
		this.isOurBase = isOurBase;
		explored = false;
		chosenForExpansion = false;
	}
	
	
	
	public Position getPosition(){
		return baseLocation.getPosition();
	}
	
	public boolean isStartLocation(){
		return baseLocation.isStartLocation();
	}
}
