/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;

import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.info.EnemyBasesInfo;
import ninja.fido.agentSCAI.base.Info;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author david_000
 */
public class UnitCommand extends CommandAgent {
	
	private ArrayList<BaseLocationInfo> enemyBases;

	
	
	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	

	public UnitCommand() throws EmptyDecisionTableMapException {
		this.enemyBases = new ArrayList<>();
	}
	
	
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());

		return defaultActivityMap;
	}
	

	@Override
	protected void processInfo(Info info) {
		if(info instanceof EnemyBasesInfo){
			enemyBases = ((EnemyBasesInfo) info).getEnemyBases();
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return new WaitGoal(this, null);
	}
	
	
    
}
