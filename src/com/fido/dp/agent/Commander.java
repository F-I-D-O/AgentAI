/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.action.Action;
import com.fido.dp.action.TestAction;

/**
 *
 * @author F.I.D.O.
 */
public class Commander extends CommandAgent {

    public Commander() {
        subordinateAgents.add(new ExplorationCommand());
    }
    
    

	@Override
	protected Action chooseAction() {
		return new TestAction(this);
	}
	
}
