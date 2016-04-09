/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.bwapiCommandInterface;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentai.agent.unit.UnitAgent;
import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.base.GameAgent;

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
