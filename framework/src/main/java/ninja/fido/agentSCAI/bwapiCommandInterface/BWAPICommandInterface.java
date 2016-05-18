/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.bwapiCommandInterface;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.GameAgent;

/**
 * Interface for BWAPI commands.
 * @author F.I.D.O.
 */
public interface BWAPICommandInterface {
	
	/**
	 * Build coomand.
	 * @param worker Worker that will construct the building.
	 * @param buildingType Building type.
	 * @param placeToBuildOn Place to build on.
	 */
	public void build(Worker worker, UnitType buildingType, TilePosition placeToBuildOn);
	
	/**
	 * Attack move command.
	 * @param agent Attacking agent.
	 * @param target Attack move target.
	 */
	public void attackMove(UnitAgent agent, Position target);
	
	/**
	 * Train command.
	 * @param agent Game agent that will train new unit.
	 * @param unitType Typpe of the unit that will be trained.
	 */
	public void train(GameAgent agent, UnitType unitType);
	
	/**
	 * Move command.
	 * @param agent Moving agent.
	 * @param target Target position.
	 */
	public void move(UnitAgent agent, Position target);
	
	/**
	 * This method should process commands queued from revious frame.
	 */
	public void processQueuedCommands();
}
