/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.goal;

import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.base.Goal;

/**
 *
 * @author F.I.D.O.
 */
public class HarvestGoal extends Goal{
	
	private final double mineralShare;

	public double getMineralShare() {
		return mineralShare;
	}
	
	
	
	public HarvestGoal(ResourceCommand agent, double mineralShare) {
		super(agent);
		this.mineralShare = mineralShare;
	}
	
}
