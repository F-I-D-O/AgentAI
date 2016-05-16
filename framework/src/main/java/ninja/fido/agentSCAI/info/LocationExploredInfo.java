/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.info;

import ninja.fido.agentSCAI.base.Info;
import bwapi.Position;
import ninja.fido.agentSCAI.base.Agent;
import ninja.fido.agentSCAI.base.CommandAgent;

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
