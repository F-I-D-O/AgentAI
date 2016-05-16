/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.bwapiCommandInterface;

import bwapi.UnitCommand;
import bwapi.Error;
import bwapi.Position;
import bwapi.PositionOrUnit;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.GameAgent;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import ninja.fido.agentSCAI.Log;

/**
 *
 * @author F.I.D.O.
 */
public class DefaultBWAPICommandInterface implements BWAPICommandInterface{
	
	private final Queue<BwapiCommad> queuedCommands;

	
	
	
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
			processBuild(new BuildCommand(worker, buildingType, placeToBuildOn));
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: cannot build here! position: {1}, building: {2}", worker.getClass(), 
					placeToBuildOn, buildingType);
		}
	}
	
	@Override
	public void attackMove(UnitAgent agent, Position target){
		UnitCommand command = UnitCommand.attack(agent.getUnit(), new PositionOrUnit(target));
		processCommand(new GeneralCommand(agent, command));
	}
	
	@Override
	public void train(GameAgent agent, UnitType unitType){
		UnitCommand command = UnitCommand.train(agent.getUnit(), unitType);
		processCommand(new GeneralCommand(agent, command));
	}
	
	@Override
	public void move(UnitAgent agent, Position target){
		UnitCommand command = UnitCommand.move(agent.getUnit(), target);
		processCommand(new GeneralCommand(agent, command));
	}
	
	private void processBuild(BuildCommand command){
		boolean success = command.getAgent().getUnit().build(command.getBuildingType(), command.getPlaceToBuildOn());
		if(!success){
			Error lastError = GameAPI.getGame().getLastError();
			if(lastError.equals(Error.Invalid_Tile_Position)){
				Log.log(this, Level.SEVERE, "{0}: Cannot build here. Position: {1}.", command.getAgent().getClass(), 
						command.getPlaceToBuildOn());
				command.getAgent().handleInvalidBuildPosition(command.getPlaceToBuildOn());
			}
			else{
				processError(command, lastError);
			}
		}
	}
	
	private void processCommand(GeneralCommand command){
		boolean success = command.getAgent().getUnit().issueCommand(command.getUnitCommand());
		if(!success){
			processError(command, GameAPI.getGame().getLastError());
		}
	}
	
	private void processError(BwapiCommad command, Error error){
		if(error.equals(Error.Unit_Busy)){
			queuedCommands.add(command);
			Log.log(this, Level.WARNING, "{0}: Unit busy, command {1} was queued.", command.getAgent().getClass(), 
					command.getType());
		}
		else{
			Log.log(this, Level.SEVERE, "{0}: Command {1} failed. Reason: {2}", command.getAgent().getClass(),
					command.getType(), error);
		}
	}

	@Override
	public void processQueuedCommands() {
		for (int i = 0; i < queuedCommands.size(); i++) {
			BwapiCommad queuedCommand = queuedCommands.poll();
			if(queuedCommand instanceof BuildCommand){
				processBuild((BuildCommand) queuedCommand);
			}
			else{
				processCommand((GeneralCommand) queuedCommand);
			}
		}
	}

}
