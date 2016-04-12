/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.info;

import bwapi.UnitType;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Request;

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
