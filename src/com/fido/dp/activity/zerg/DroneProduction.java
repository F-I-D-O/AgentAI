/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.zerg;

import bwapi.UnitType;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.agent.LarvaCommand;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import com.fido.dp.order.LarvaMorph;
import com.fido.dp.request.ResourceRequest;
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
		
//		int mineralsMissing = 0;
//		int supplymissing = 0;
		
		int droneMineralPrice = UnitType.Zerg_Drone.mineralPrice();
		int droneSupplyPrice = UnitType.Zerg_Drone.supplyRequired();
//		int droneSupplyPrice = 1;
		for (Larva larva : larvas) {
			
			if(larva.getUnit().isMorphing()){
				continue;
			}
			
			// if we have enought resources
			if(agent.getOwnedSupply() >= droneSupplyPrice && agent.getOwnedMinerals() >= droneMineralPrice){
				try {
					agent.giveResource(larva, ResourceType.MINERALS, droneMineralPrice);
					agent.giveResource(larva, ResourceType.SUPPLY, droneSupplyPrice);
				} catch (ResourceDeficiencyException ex) {
					ex.printStackTrace();
				}
				
				new LarvaMorph(larva, agent, Larva.MorphOption.DRONE).issueOrder();
			}
			// else
			else{
				int mineralsMissing = 0;
				int supplymissing = 0;
				if(agent.getOwnedSupply() < droneSupplyPrice){
					supplymissing = droneSupplyPrice - agent.getOwnedSupply();
					
				}	
				if(agent.getOwnedMinerals() < droneMineralPrice){
					mineralsMissing = droneMineralPrice - agent.getOwnedMinerals();	
				}
				new ResourceRequest(agent.getCommandAgent(), agent, mineralsMissing, 0, supplymissing).send();
			}
		}
//		if(mineralsMissing > 0 || supplymissing > 0){
//			new ResourceRequest(agent.getCommandAgent(), agent, mineralsMissing, 0, supplymissing).send();
//		}
	}

	@Override
	protected void init() {
		
	}
	
}
