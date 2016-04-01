/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.terran;

import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.agent.unit.Barracks;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.AutomaticProductionGoal;
import com.fido.dp.order.AutomaticProductionOrder;
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
			if(barracks.isMineralsMissing() && barracks.getMissingMinerals() <= agent.getOwnedMinerals()){
				try {
					agent.giveResource(barracks, ResourceType.MINERALS, barracks.getMissingMinerals());
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
