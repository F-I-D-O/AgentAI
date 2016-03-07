/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import bwapi.Position;
import com.fido.dp.Scout;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 */
public class ExploreBaseLocationGoal extends Goal {
	
	private final Position baseLocation;

	
	
	public Position getBaseLocation() {
		return baseLocation;
	}
	
	
	
	public ExploreBaseLocationGoal(Scout agent, Position baseLocation) {
		super((Agent) agent);
		this.baseLocation = baseLocation;
	}
	
}
