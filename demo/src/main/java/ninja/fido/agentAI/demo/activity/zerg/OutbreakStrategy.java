/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.demo.activity.zerg;

import bwapi.UnitType;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.agent.unit.Drone;
import ninja.fido.agentAI.agent.ZergCommander;
import ninja.fido.agentAI.agent.unit.Larva;
import ninja.fido.agentAI.agent.unit.Overlord;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandActivity;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.base.Request;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.info.CodeMessageInfo;
import ninja.fido.agentAI.order.DetachBack;
import ninja.fido.agentAI.request.ResourceRequest;
import java.util.List;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategy extends CommandActivity<ZergCommander,Goal,OutbreakStrategy>
		implements DecisionModuleActivity<ZergCommander,Goal,OutbreakStrategy>{
	
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
	protected void performAction() throws ChainOfCommandViolationException {
		List<Larva> larvas = agent.getCommandedAgents(Larva.class);
		List<Drone> drones = agent.getCommandedAgents(Drone.class);
		
		// saturating exploration command
		if(!drones.isEmpty()){           
            for (Drone drone : drones) {
                if(agent.getSubordinateAgentsDetachedTo(agent.explorationCommand, Drone.class) < targetNumberOfScouts){
                    detachCommandedAgent(drone, agent.explorationCommand);
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
				detachCommandedAgent(drone, agent.expansionCommand);
				expandingDrones++;
			}
		}
		
		drones = agent.getCommandedAgents(Drone.class);
		
		detachCommandedAgents(drones, agent.resourceCommand);
		detachCommandedAgents(larvas, agent.larvaCommand);
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
