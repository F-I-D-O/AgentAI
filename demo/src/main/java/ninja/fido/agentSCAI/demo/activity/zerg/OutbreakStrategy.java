/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.zerg;

import bwapi.UnitType;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.agent.unit.Drone;
import ninja.fido.agentSCAI.agent.ZergCommander;
import ninja.fido.agentSCAI.agent.unit.Larva;
import ninja.fido.agentSCAI.agent.unit.Overlord;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.Request;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.info.CodeMessageInfo;
import ninja.fido.agentSCAI.order.DetachBack;
import ninja.fido.agentSCAI.request.ResourceRequest;
import java.util.List;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

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
		List<Larva> larvas = getCommandedAgents(Larva.class);
		List<Drone> drones = getCommandedAgents(Drone.class);
		
		// saturating exploration command
		if(!drones.isEmpty()){           
            for (Drone drone : drones) {
                if(getSubordinateAgentsDetachedTo(agent.explorationCommand, Drone.class) < targetNumberOfScouts){
                    detachCommandedAgent(drone, agent.explorationCommand);
                }
				else{
					break;
				}
            }
        }
		
		// base expansions
		if(getSubordinateAgentsDetachedTo(agent.resourceCommand, Drone.class) / 4 > expandingDrones){
			Drone drone = getCommandedAgent(Drone.class);
			if(drone == null){
				new DetachBack(agent.resourceCommand, agent, Drone.class, 1).issueOrder();
			}
			else{
				detachCommandedAgent(drone, agent.expansionCommand);
				expandingDrones++;
			}
		}
		
		drones = getCommandedAgents(Drone.class);
		
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
				if(materialRequest.getMineralAmount() <= getOwnedMinerals() 
						&& materialRequest.getGasAmount() <= getOwnedGas()){
					try {
						giveResource(materialRequest.getSender(), ResourceType.MINERALS, materialRequest.getMineralAmount());
						giveResource(materialRequest.getSender(), ResourceType.GAS, materialRequest.getGasAmount());
					} 
					catch (ResourceDeficiencyException ex) {
						ex.printStackTrace();
					}
				}
				else {
					materialRequest.send();
				}
			}
			else if(request.getSender() == agent.larvaCommand){
				if(materialRequest.getMineralAmount() <= getOwnedMinerals() 
						&& materialRequest.getSupplyAmount() <= getOwnedSupply()){
					if(getMineralsGivenTo(agent.larvaCommand) < DRONE_LIMIT_PER_BASE * UnitType.Zerg_Drone.mineralPrice()){
						try {
							giveResource(materialRequest.getSender(), ResourceType.MINERALS, materialRequest.getMineralAmount());
							giveResource(materialRequest.getSender(), ResourceType.SUPPLY, materialRequest.getSupplyAmount());
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
