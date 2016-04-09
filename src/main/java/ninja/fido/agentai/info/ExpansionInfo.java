package ninja.fido.agentai.info;


import bwapi.Position;
import ninja.fido.agentai.agent.ExplorationCommand;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Info;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfo extends Info{
	
	private final Position expansionPosition;

	public Position getExpansionPosition() {
		return expansionPosition;
	}
	
	
	
	public ExpansionInfo(Agent recipient, ExplorationCommand sender, Position expansionPosition) {
		super(recipient, sender);
		this.expansionPosition = expansionPosition;
	}
	
}
