/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.info;

import bwapi.UnitType;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Request;

/**
 *
 * @author F.I.D.O.
 */
public class UnitCreationStartedInfo extends Request{
	
	private final UnitType unitType;

	
	
	
	public UnitType getUnitType() {
		return unitType;
	}
	
	
	
	public UnitCreationStartedInfo(CommandAgent recipient, Agent sender, UnitType unitType) {
		super(recipient, sender);
		this.unitType = unitType;
	}
	
}
