/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.info;

import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.base.Agent;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBasesInfo extends Info{
	
	private final ArrayList<BaseLocationInfo> enemyBases;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	
	
	public EnemyBasesInfo(Agent recipient, Agent sender, ArrayList<BaseLocationInfo> enemyBases) {
		super(recipient, sender);
		this.enemyBases = enemyBases;
	}
	
}
