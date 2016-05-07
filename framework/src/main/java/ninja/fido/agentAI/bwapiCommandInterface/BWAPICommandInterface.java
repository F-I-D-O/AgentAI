/* 
 * AgentAI
 */
package ninja.fido.agentAI.bwapiCommandInterface;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.agent.unit.Worker;
import ninja.fido.agentAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public interface BWAPICommandInterface {
		
	public void build(Worker worker, UnitType buildingType, TilePosition placeToBuildOn);
	
	public void attackMove(UnitAgent agent, Position target);
	
	public void train(GameAgent agent, UnitType unitType);
	
	public void move(UnitAgent agent, Position target);
	
	public void processQueuedCommands();
}
