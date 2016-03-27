/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.zerg;

import bwapi.UnitType;
import com.fido.dp.Material;
import com.fido.dp.agent.unit.Drone;
import com.fido.dp.agent.ZergCommander;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.base.Goal;
import com.fido.dp.base.Request;
import com.fido.dp.order.ConstructBuildingOrder;
import com.fido.dp.order.DetachBack;
import com.fido.dp.request.MaterialRequest;
import java.util.List;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakStrategy extends CommandActivity<ZergCommander, Goal>{
	
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
	public void initialize(Goal goal) {
		targetNumberOfScouts = 1;
	}
	
	

	@Override
	protected void performAction() {
		List<Larva> larvas = agent.getSubordinateAgents(Larva.class);
		List<Drone> drones = agent.getSubordinateAgents(Drone.class);
		
		// saturating exploration command
		if(!drones.isEmpty()){           
            for (Drone drone : drones) {
                if(agent.explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachSubordinateAgent(drone, agent.explorationCommand);
                }
				else{
					break;
				}
            }
        }
		
		// base expansions
		if(agent.resourceCommand.getSubordinateAgents(Drone.class).size() / 4 > expandingDrones){
			Drone drone = agent.getSubordinateAgent(Drone.class);
			if(drone == null){
				new DetachBack(agent.resourceCommand, agent, Drone.class, 1).issueOrder();
			}
			else{
				agent.detachSubordinateAgent(drone, agent.expansionCommand);
				expandingDrones++;
			}
		}
		
		drones = agent.getSubordinateAgents(Drone.class);
		
		agent.detachSubordinateAgents(drones, agent.resourceCommand);
		agent.detachSubordinateAgents(larvas, agent.larvaCommand);
	}

	@Override
	protected void init() {
//		List<Drone> drones = agent.getSubordinateAgents(Drone.class);
		
		
	}

	@Override
	protected void handleRequest(Request request) {
		if(request instanceof MaterialRequest){
			if(request.getSender() == agent.expansionCommand){
				MaterialRequest materialRequest = (MaterialRequest) request;
				if(materialRequest.getMineralAmount() <= agent.getOwnedMinerals() 
						&& materialRequest.getGasAmount() <= agent.getOwnedGas()){
					agent.giveSupply(materialRequest.getSender(), Material.MINERALS, materialRequest.getMineralAmount());
					agent.giveSupply(materialRequest.getSender(), Material.GAS, materialRequest.getGasAmount());
				}
				else {
					agent.queRequest(materialRequest);
				}
			}
		}
	}
	
	
}
