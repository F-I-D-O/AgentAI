/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.bwapiCommandInterface;

import bwapi.UnitCommand;
import ninja.fido.agentAI.base.GameAgent;

/**
 *
 * @author F.I.D.O.
 */
final class GeneralCommand extends BwapiCommad{
	
	private final UnitCommand unitCommand;

	public UnitCommand getUnitCommand() {
		return unitCommand;
	}
	
	
	
	
	public GeneralCommand(GameAgent agent, UnitCommand unitCommand) {
		super(agent);
		this.unitCommand = unitCommand;
	}

	@Override
	public String getType() {
		return unitCommand.getUnitCommandType().toString();
	}
	
}
