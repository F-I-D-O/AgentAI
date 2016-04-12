/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.bwapiCommandInterface;

import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentAI.agent.unit.Worker;

/**
 *
 * @author F.I.D.O.
 */
final class BuildCommand extends BwapiCommad<Worker>{
	
	private final UnitType buildingType;
	
	private final TilePosition placeToBuildOn;

	
	
	
	public UnitType getBuildingType() {
		return buildingType;
	}

	public TilePosition getPlaceToBuildOn() {
		return placeToBuildOn;
	}
	
	
	
	public BuildCommand(final Worker agent, final UnitType buildingType, final TilePosition placeToBuildOn) {
		super(agent);
		this.buildingType = buildingType;
		this.placeToBuildOn = placeToBuildOn;
	}

	@Override
	public String getType() {
		return "BUILD";
	}
	
}
