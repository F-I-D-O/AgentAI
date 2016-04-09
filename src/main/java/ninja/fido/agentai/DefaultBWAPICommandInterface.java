/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import bwapi.UnitCommand;
import bwapi.Error;
import bwapi.Position;
import bwapi.PositionOrUnit;
import bwapi.TilePosition;
import bwapi.UnitCommandType;
import bwapi.UnitType;
import ninja.fido.agentai.agent.unit.UnitAgent;
import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.GameAgent;
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
	public void build(Worker worker, UnitType buildingType, TilePosition placeToBuildOn){
		if(placeToBuildOn == null){
			Log.log(this, Level.SEVERE, "{0}: place to build on is null!", worker.getClass());
			return;
		}
		
		if(GameAPI.getGame().canBuildHere(placeToBuildOn, buildingType)){
			boolean success = worker.getUnit().build(buildingType, placeToBuildOn);
//			boolean success = worker.getUnit().issueCommand(UnitCommand.build(worker.getUnit(), placeToBuildOn, buildingType));
//			processBuild(worker, UnitCommand.build(worker.getUnit(), placeToBuildOn, buildingType));
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: cannot build here! position: {1}, building: {2}", worker.getClass(), 
					placeToBuildOn, buildingType);
		}
	}
	
	@Override
	public void attackMove(UnitAgent agent, Position target){
		UnitCommand command = UnitCommand.attack(agent.getUnit(), new PositionOrUnit(target));
		processCommand(agent, command);
	}
	
	@Override
	public void train(GameAgent agent, UnitType unitType){
		UnitCommand command = UnitCommand.train(agent.getUnit(), unitType);
		processCommand(agent, command);
	}
	
	@Override
	public void move(UnitAgent agent, Position target){
		UnitCommand command = UnitCommand.move(agent.getUnit(), target);
		processCommand(agent, command);
	}
	
	private void processBuild(Worker worker, UnitCommand unitCommand){
		boolean success = worker.getUnit().issueCommand(unitCommand);
		if(!success){
			Error lastError = GameAPI.getGame().getLastError();
			if(lastError.equals(Error.Invalid_Tile_Position)){
				Log.log(this, Level.SEVERE, "{0}: Cannot build here. Position: {1}, building: {2}", 
						worker.getClass(), unitCommand.getTargetTilePosition(), null);
				worker.handleInvalidBuildPosition(unitCommand.getTargetTilePosition(), unitCommand.getUnit());
//				try {
//					throw new InvalidTilePositionException(agent, unitCommand.getTargetTilePosition());
//				} catch (InvalidTilePositionException ex) {
//					ex.printStackTrace();
//				}
			}
			else{
				processError(worker, unitCommand, lastError);
			}
		}
	}
	
	private void processCommand(GameAgent agent, UnitCommand unitCommand){
		boolean success = agent.getUnit().issueCommand(unitCommand);
		if(!success){
			processError(agent, unitCommand, GameAPI.getGame().getLastError());
		}
	}
	
	private void processError(GameAgent agent, UnitCommand unitCommand, Error error){
		if(error.equals(Error.Unit_Busy)){
			queuedCommands.add(new QueuedCommand(agent, unitCommand));
			Log.log(this, Level.WARNING, "{0}: Unit busy, command {1} was queued.", agent.getClass(), 
					unitCommand.getUnitCommandType());
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: Command {1} failed. Reason: {2}", agent.getClass(),
					unitCommand.getUnitCommandType(), error);
		}
	}

	@Override
	public void processQueuedCommands() {
		for (int i = 0; i < queuedCommands.size(); i++) {
			QueuedCommand queuedCommand = queuedCommands.poll();
			if(queuedCommand.unitCommand.getUnitCommandType().equals(UnitCommandType.Build)){
				processBuild((Worker) queuedCommand.agent, queuedCommand.unitCommand);
			}
			else{
				processCommand(queuedCommand.agent, queuedCommand.unitCommand);
			}
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
