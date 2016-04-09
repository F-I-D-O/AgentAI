/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.terran;

import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.agent.unit.Barracks;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.agent.ProductionCommand;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.AutomaticProductionGoal;
import ninja.fido.agentai.order.AutomaticProductionOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author F.I.D.O.
 */
public class BBSProduction extends CommandActivity<ProductionCommand,Goal>{

	public BBSProduction(ProductionCommand agent) {
		super(agent);
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof BBSProduction;
	}
	
	

	@Override
	protected void performAction() {
		for (Barracks barracks : agent.getBarracks()) {
			if(!(barracks.getGoal() instanceof AutomaticProductionGoal)){
				new AutomaticProductionOrder(barracks, agent).issueOrder();
			}
			if(barracks.isMineralsMissing() && barracks.getMissingMinerals() <= agent.getOwnedMinerals()
					&& barracks.isSupplyMissing() && barracks.getMissingSupply() <= agent.getOwnedSupply()){
				try {
					agent.giveResource(barracks, ResourceType.MINERALS, barracks.getMissingMinerals());
					agent.giveResource(barracks, ResourceType.SUPPLY, barracks.getMissingSupply());
				} catch (ResourceDeficiencyException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
}
