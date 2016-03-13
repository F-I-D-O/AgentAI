/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.Material;
import com.fido.dp.agent.Barracks;
import com.fido.dp.base.CommandAction;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.goal.AutomaticProductionGoal;
import com.fido.dp.order.AutomaticProductionOrder;

/**
 *
 * @author F.I.D.O.
 */
public class BBSProduction extends CommandAction<ProductionCommand>{

	public BBSProduction(ProductionCommand agent) {
		super(agent);
	}

	@Override
	protected void performAction() {
		for (Barracks barracks : agent.getBarracks()) {
			if(!(barracks.getGoal() instanceof AutomaticProductionGoal)){
				new AutomaticProductionOrder(barracks, agent).issueCommand();
			}
			if(barracks.isMineralsMissing() && barracks.getMissingMinerals() <= agent.getOwnedMinerals()){
				agent.giveSupply(barracks, Material.MINERALS, barracks.getMissingMinerals());
			}
		}
	}

	@Override
	protected void init() {
		
	}
	
}
