/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.zerg;

import com.fido.dp.agent.LarvaCommand;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import com.fido.dp.order.LarvaMorph;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class DroneProduction extends CommandActivity<LarvaCommand, Goal>{

	public DroneProduction(LarvaCommand agent) {
		super(agent);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof DroneProduction;
	}

	@Override
	public void initialize(Goal goal) {
		
	}
	
	
	

	@Override
	protected void performAction() {
		List<Larva> larvas = agent.getCommandedAgents(Larva.class);
		
		for (Larva larva : larvas) {
			if(agent.getOwnedMinerals() >= larva.getUnit().getType().gasPrice() 
					&& agent.getOwnedSupply() >= larva.getUnit().getType().supplyRequired()){
				new LarvaMorph(larva, agent, Larva.MorphOption.DRONE).issueOrder();
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
}
