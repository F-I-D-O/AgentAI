/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.zerg;

import bwapi.UnitType;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.agent.LarvaCommand;
import ninja.fido.agentSCAI.agent.unit.Larva;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.info.CodeMessageInfo;
import ninja.fido.agentSCAI.order.LarvaMorph;
import ninja.fido.agentSCAI.request.ResourceRequest;
import java.util.List;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author F.I.D.O.
 */
public class OutbreakProduction extends CommandActivity<LarvaCommand,Goal,OutbreakProduction>
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
		List<Larva> larvas = getCommandedAgents(Larva.class);
		
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
			if(getOwnedSupply() >= supplyPrice && getOwnedMinerals() >= mineralPrice){
				try {
					giveResource(larva, ResourceType.MINERALS, mineralPrice);
					giveResource(larva, ResourceType.SUPPLY, supplyPrice);
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
				if(getOwnedSupply() < supplyPrice){
					supplymissing = supplyPrice - getOwnedSupply();
				}	
				if(getOwnedMinerals() < mineralPrice){
					mineralsMissing = mineralPrice - getOwnedMinerals();	
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
