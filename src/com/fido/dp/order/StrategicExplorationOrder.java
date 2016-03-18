/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.order;

import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GoalOrder;

/**
 *
 * @author david
 */
public class StrategicExplorationOrder extends GoalOrder{

	public StrategicExplorationOrder(ExplorationCommand target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}

	@Override
	protected void execute() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
