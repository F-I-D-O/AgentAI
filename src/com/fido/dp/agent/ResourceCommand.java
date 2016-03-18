/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Action;
import com.fido.dp.action.ManageHarvest;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.HarvestGoal;

/**
 *
 * @author david_000
 */
public class ResourceCommand extends CommandAgent{
    
    public static final int MINERAL_SHARE_MINERALS_ONLY = 1;

    @Override
    protected Action chooseAction() {
        if(getGoal() instanceof HarvestGoal){
			HarvestGoal goal = getGoal();
			return new ManageHarvest(this, goal.getMineralShare());
		}
		return null;
    }

	@Override
	protected Goal getDefaultGoal() {
		return new HarvestGoal(this, MINERAL_SHARE_MINERALS_ONLY);
	}
}
