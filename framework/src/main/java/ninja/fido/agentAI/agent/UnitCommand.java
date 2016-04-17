/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import ninja.fido.agentAI.BaseLocationInfo;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.info.EnemyBasesInfo;
import ninja.fido.agentAI.base.Info;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.Wait;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.goal.WaitGoal;

/**
 *
 * @author david_000
 */
public class UnitCommand extends CommandAgent {
	
	private ArrayList<BaseLocationInfo> enemyBases;

	
	
	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	

	public UnitCommand() {
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
