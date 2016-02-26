/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Unit;
import com.fido.dp.action.Action;
import com.fido.dp.action.HarvestMineralsAction;

/**
 *
 * @author F.I.D.O.
 */
public class SCV extends LeafAgent {
	

	public SCV(Unit unit) {
		super(unit);
	}

	
	public void commandHarvest(){
		commandedAction = new HarvestMineralsAction(this);
	}

	@Override
	protected Action chooseAction() {
//		Action action = null;
//		if(commandedAction instanceof HarvestMineralsAction){
//			action = commandedAction;
//		}
//		return action;
        return commandedAction;
	}
}
