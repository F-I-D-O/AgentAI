/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.UnitCommand;
import bwapi.Error;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.GameAgent;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultBWAPICommandInterface implements BWAPICommandInterface{
	
	private final Queue<QueuedCommand> queuedCommands;

	
	
	
	public DefaultBWAPICommandInterface() {
		queuedCommands = new ArrayDeque<>();
	}
	

	
	
	@Override
	public void issueCommand(GameAgent agent, UnitCommand unitCommand){
		boolean success = agent.getUnit().issueCommand(unitCommand);
		if(!success){
			Error lastError = GameAPI.getGame().getLastError();
			if(lastError.equals(Error.Unit_Busy)){
				queuedCommands.add(new QueuedCommand(agent, unitCommand));
				Log.log(this, Level.SEVERE, "{0}: Unit busy, command {1} was queued.", agent.getClass(), 
						unitCommand.getUnitCommandType());
			}
			else{
				Log.log(this, Level.SEVERE, "{0}: Command {1} failed. Reason: {2}", agent.getClass(), unitCommand, 
						lastError);
			}
		}
	}

	@Override
	public void processQueuedCommands() {
		QueuedCommand queuedCommand;
		while ((queuedCommand = queuedCommands.poll()) != null) {
			issueCommand(queuedCommand.agent, queuedCommand.unitCommand);
		}
	}

	private class QueuedCommand {

		public GameAgent agent;
		
		public UnitCommand unitCommand;

		
		
		
		public QueuedCommand(GameAgent agent, UnitCommand unitCommand) {
			this.agent = agent;
			this.unitCommand = unitCommand;
		}
		
	}
}
