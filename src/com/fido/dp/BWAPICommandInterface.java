/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.UnitCommand;
import com.fido.dp.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
public interface BWAPICommandInterface {
	
	public void issueCommand(GameAgent agent, UnitCommand unitCommand);
	
	public void processQueuedCommands();
}
