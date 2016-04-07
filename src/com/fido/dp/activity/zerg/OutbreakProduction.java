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
import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.base.Info;
import com.fido.dp.decisionMaking.DecisionModuleActivity;
import com.fido.dp.info.CodeMessageInfo;
import com.fido.dp.order.LarvaMorph;
import com.fido.dp.request.ResourceRequest;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakProduction extends CommandActivity<LarvaCommand, Goal>
		implements DecisionModuleActivity<LarvaCommand, Goal, OutbreakProduction>{
	
	private boolean overlordInProduction;

	
	
	
	public OutbreakProduction() {
	}

	public OutbreakProduction(LarvaCommand agent) {
		super(agent);
		overlordInProduction = false;
	}

	public OutbreakProduction(LarvaCommand agent, Goal goal) {
		super(agent, goal);
	}

	
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof OutbreakProduction;
	}

	@Override
	public void initialize(LarvaCommand agent, Goal goal) {
		super.initialize(agent, goal);
	}
	
	
	

	@Override
	protected void performAction() {
		List<Larva> larvas = agent.getCommandedAgents(Larva.class);
		
		UnitType unitToBuild;
		Larva.MorphOption morphOption;
		for (Larva larva : larvas) {
			
			if(larva.getUnit().isMorphing()){
				continue;
			}
			
			// limit for testing
			if(GameAPI.getGame().self().supplyUsed() > 12){
				break;
			}
			
			if(!overlordInProduction && GameAPI.getGame().self().supplyTotal() - GameAPI.getGame().self().supplyUsed() < 3){
				unitToBuild = UnitType.Zerg_Overlord;
				morphOption = Larva.MorphOption.OVERLORD;
			}
			else{
				unitToBuild = UnitType.Zerg_Drone;
				morphOption = Larva.MorphOption.DRONE;
			}
			
			int mineralPrice = unitToBuild.mineralPrice();
			int supplyPrice = unitToBuild.supplyRequired();
			
			// if we have enought resources
			if(agent.getOwnedSupply() >= supplyPrice && agent.getOwnedMinerals() >= mineralPrice){
				try {
					agent.giveResource(larva, ResourceType.MINERALS, mineralPrice);
					agent.giveResource(larva, ResourceType.SUPPLY, supplyPrice);
					new LarvaMorph(larva, agent, morphOption).issueOrder();
					if(morphOption == Larva.MorphOption.OVERLORD){
						overlordInProduction = true;
					}
				} catch (ResourceDeficiencyException ex) {
					ex.printStackTrace();
				}
			}
			// else
			else{
				int mineralsMissing = 0;
				int supplymissing = 0;
				if(agent.getOwnedSupply() < supplyPrice){
					supplymissing = supplyPrice - agent.getOwnedSupply();
				}	
				if(agent.getOwnedMinerals() < mineralPrice){
					mineralsMissing = mineralPrice - agent.getOwnedMinerals();	
				}
				new ResourceRequest(agent.getCommandAgent(), agent, mineralsMissing, 0, supplymissing).send();
				break;
			}
		}
	}

	@Override
	protected void init() {
		
	}

	@Override
	protected void processInfo(Info info) {
		if(info instanceof CodeMessageInfo){
			switch(((CodeMessageInfo) info).getCode()){
				case OVERLORD_MORPHED:
					overlordInProduction = false;
					break;
			}
		}
	}

	@Override
	public OutbreakProduction create(LarvaCommand agent, Goal goal) {
		return new OutbreakProduction(agent, goal);
	}
	
	
	
}
