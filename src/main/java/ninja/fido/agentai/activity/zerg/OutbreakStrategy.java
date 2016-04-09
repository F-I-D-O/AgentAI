/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.zerg;

import bwapi.UnitType;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.agent.unit.Drone;
import ninja.fido.agentai.agent.ZergCommander;
import ninja.fido.agentai.agent.unit.Larva;
import ninja.fido.agentai.agent.unit.Overlord;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.Request;
import ninja.fido.agentai.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentai.info.CodeMessageInfo;
import ninja.fido.agentai.order.DetachBack;
import ninja.fido.agentai.request.ResourceRequest;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategy extends CommandActivity<ZergCommander, Goal>
		implements DecisionModuleActivity<ZergCommander, Goal, OutbreakStrategy>{
	
	private static final int DRONE_LIMIT_PER_BASE = 10;
	
	private int targetNumberOfScouts;
	
	private int expandingDrones;

	
	
	
	public OutbreakStrategy() {
	}

	public OutbreakStrategy(ZergCommander agent) {
		super(agent);
		targetNumberOfScouts = 1;
		expandingDrones = 0;
	}
	
	
	

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == OutOfMemoryError.class;
	}
	
	

	@Override
	protected void performAction() {
		List<Larva> larvas = agent.getCommandedAgents(Larva.class);
		List<Drone> drones = agent.getCommandedAgents(Drone.class);
		
		// saturating exploration command
		if(!drones.isEmpty()){           
            for (Drone drone : drones) {
                if(agent.getSubordinateAgentsDetachedTo(agent.explorationCommand, Drone.class) < targetNumberOfScouts){
                    getAgent().detachCommandedAgent(drone, agent.explorationCommand);
                }
				else{
					break;
				}
            }
        }
		
		// base expansions
		if(agent.resourceCommand.getCommandedAgents(Drone.class).size() / 4 > expandingDrones){
			Drone drone = agent.getCommandedAgent(Drone.class);
			if(drone == null){
				new DetachBack(agent.resourceCommand, agent, Drone.class, 1).issueOrder();
			}
			else{
				agent.detachCommandedAgent(drone, agent.expansionCommand);
				expandingDrones++;
			}
		}
		
		drones = agent.getCommandedAgents(Drone.class);
		
		agent.detachCommandedAgents(drones, agent.resourceCommand);
		agent.detachCommandedAgents(larvas, agent.larvaCommand);
		

	}

	@Override
	protected void init() {
//		List<Drone> drones = agent.getCommandedAgents(Drone.class);
		
		
	}

	@Override
	protected void handleRequest(Request request) {
		if(request instanceof ResourceRequest){
			ResourceRequest materialRequest = (ResourceRequest) request;
			if(request.getSender() == agent.expansionCommand){
				if(materialRequest.getMineralAmount() <= agent.getOwnedMinerals() 
						&& materialRequest.getGasAmount() <= agent.getOwnedGas()){
					try {
						agent.giveResource(materialRequest.getSender(), ResourceType.MINERALS, materialRequest.getMineralAmount());
						agent.giveResource(materialRequest.getSender(), ResourceType.GAS, materialRequest.getGasAmount());
					} 
					catch (ResourceDeficiencyException ex) {
						ex.printStackTrace();
					}
				}
				else {
					agent.queRequest(materialRequest);
				}
			}
			else if(request.getSender() == agent.larvaCommand){
				if(materialRequest.getMineralAmount() <= agent.getOwnedMinerals() 
						&& materialRequest.getSupplyAmount() <= agent.getOwnedSupply()){
					if(agent.getMineralsGivenTo(agent.larvaCommand) < DRONE_LIMIT_PER_BASE * UnitType.Zerg_Drone.mineralPrice()){
						try {
							agent.giveResource(materialRequest.getSender(), ResourceType.MINERALS, materialRequest.getMineralAmount());
							agent.giveResource(materialRequest.getSender(), ResourceType.SUPPLY, materialRequest.getSupplyAmount());
						} 
						catch (ResourceDeficiencyException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	@Override
	protected void onCommandedAgentAdded(Agent commandedAgent) {
		if(commandedAgent instanceof Overlord){
			new CodeMessageInfo(CodeMessageInfo.Code.OVERLORD_MORPHED, agent.larvaCommand, agent).send();
		}
	}

	@Override
	public OutbreakStrategy create(ZergCommander agent, Goal goal) {
		return new OutbreakStrategy(agent);
	}
	
	
	
}
