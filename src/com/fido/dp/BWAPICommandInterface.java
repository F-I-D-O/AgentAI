/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.agent.unit.UnitAgent;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.base.GameAgent;

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
