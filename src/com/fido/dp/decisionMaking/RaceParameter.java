/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import bwapi.Race;
import com.fido.dp.base.Agent;
import com.fido.dp.base.GameAPI;

/**
 *
 * @author F.I.D.O.
 */
public class RaceParameter extends DecisionTablesMapParametr<Agent, Race, RaceParameter>{

	public RaceParameter(Race value) {
		super(value);
	}

	@Override
	public RaceParameter getCurrentParameter(Agent agent) {
		return new RaceParameter(GameAPI.getGame().self().getRace());
	}
	
}
