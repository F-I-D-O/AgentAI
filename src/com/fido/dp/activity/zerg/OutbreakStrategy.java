/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.zerg;

import bwapi.UnitType;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.agent.unit.Drone;
import com.fido.dp.agent.ZergCommander;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.agent.unit.Overlord;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.Request;
import com.fido.dp.info.CodeMessageInfo;
import com.fido.dp.order.DetachBack;
import com.fido.dp.request.ResourceRequest;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategy extends CommandActivity<ZergCommander, Goal>{
	
	private static final int DRONE_LIMIT_PER_BASE = 10;
	
	private int targetNumberOfScouts;
	
	private int expandingDrones;

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
	public void initialize(ZergCommander agent, Goal goal) {
		super.initialize(agent, goal);
		targetNumberOfScouts = 1;
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
	
	
	
}
