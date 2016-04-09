/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent.unit;

import bwapi.Position;
import bwapi.Unit;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class UnitAgent extends GameAgent{
	
	public UnitAgent(Unit unit) {
		super(unit);
	}
	
	public void move(Position position){
		GameAPI.move(this, position);
	}
}
