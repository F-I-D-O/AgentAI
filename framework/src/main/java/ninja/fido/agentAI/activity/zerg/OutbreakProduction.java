/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.activity.zerg;

import bwapi.UnitType;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.agent.LarvaCommand;
import ninja.fido.agentAI.agent.unit.Larva;
import ninja.fido.agentAI.base.CommandActivity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.Info;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.info.CodeMessageInfo;
import ninja.fido.agentAI.order.LarvaMorph;
import ninja.fido.agentAI.request.ResourceRequest;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

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

	
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof OutbreakProduction;
	}
	
	
	

	@Override
	protected void performAction() throws ChainOfCommandViolationException {
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
		return new OutbreakProduction(agent);
	}
	
	
	
}
