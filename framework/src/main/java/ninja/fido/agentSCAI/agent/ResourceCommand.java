/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;

import java.util.HashMap;
import java.util.Map;
import ninja.fido.agentSCAI.activity.ManageHarvest;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.goal.HarvestGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author david_000
 */
public class ResourceCommand extends CommandAgent{
    
    public static final int MINERAL_SHARE_MINERALS_ONLY = 1;

	public ResourceCommand() throws EmptyDecisionTableMapException {
	}
	

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
