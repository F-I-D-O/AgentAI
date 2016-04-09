/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.activity.ManageHarvest;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.HarvestGoal;

/**
 *
 * @author david_000
 */
public class ResourceCommand extends CommandAgent{
    
    public static final int MINERAL_SHARE_MINERALS_ONLY = 1;

    @Override
    protected Activity chooseAction() {
        if(getGoal() instanceof HarvestGoal){
			HarvestGoal goal = getGoal();
			return new ManageHarvest(this, goal.getMineralShare());
		}
		return null;
    }

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestGoal(this, null, MINERAL_SHARE_MINERALS_ONLY);
	}
}
