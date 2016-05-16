/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.info;

import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBaseDiscovered extends Info{
	
	private final BaseLocationInfo baseInfo;

	
	
	
	public BaseLocationInfo getBaseInfo() {
		return baseInfo;
	}
	
	
	
	public EnemyBaseDiscovered(Agent recipient, Agent sender, BaseLocationInfo baseInfo) {
		super(recipient, sender);
		this.baseInfo = baseInfo;
	}
	
}
