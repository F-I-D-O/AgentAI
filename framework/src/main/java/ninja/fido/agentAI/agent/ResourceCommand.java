/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.agent;

import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentAI.activity.ManageHarvest;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.CommandAgent;
import ninja.fido.agentAI.base.Goal;
import ninja.fido.agentAI.goal.HarvestGoal;

/**
 *
 * @author david_000
 */
public class ResourceCommand extends CommandAgent{
    
    public static final int MINERAL_SHARE_MINERALS_ONLY = 1;

	@Override
	public Map<Class<? extends Goal>, Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();
		
		defaultActivityMap.put(HarvestGoal.class, new ManageHarvest());
		
		return defaultActivityMap;
	}
	
	

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestGoal(this, null, MINERAL_SHARE_MINERALS_ONLY);
	}
	
}
