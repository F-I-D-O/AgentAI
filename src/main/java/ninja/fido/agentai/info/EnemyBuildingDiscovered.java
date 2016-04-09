/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.info;

import ninja.fido.agentai.base.Info;
import bwapi.Unit;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBuildingDiscovered extends Info {
	private final Unit building;

	
	
	
	public Unit getBuilding() {
		return building;
	}
	
	
	

	public EnemyBuildingDiscovered(CommandAgent recipient, GameAgent sender,Unit building) {
		super(recipient, sender);
		this.building = building;
	}
}
