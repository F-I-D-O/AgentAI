/* 
 * AgentAI
 */
package ninja.fido.agentAI.info;

import ninja.fido.agentAI.base.Info;
import bwapi.Position;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandAgent;

/**
 *
 * @author F.I.D.O.
 */
public class LocationExploredInfo extends Info{
	
	private final Position baseLocation;

	
	
	
	public Position getBaseLocation() {
		return baseLocation;
	}
	
	
	
	
	
	public LocationExploredInfo(CommandAgent recipient, Agent sender, Position baseLocation) {
		super(recipient, sender);
		this.baseLocation = baseLocation;
	}
	
}
