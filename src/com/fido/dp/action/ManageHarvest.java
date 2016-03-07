/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.agent.SCV;
import com.fido.dp.command.HarvestMineralsCommand;
import com.fido.dp.goal.HarvestMineralsGoal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author david_000
 */
public class ManageHarvest extends CommandAction{
	
	private final double mineralShare;

    public ManageHarvest(CommandAgent agent, double mineralShare) {
        super(agent);
        this.mineralShare = mineralShare;
    }

    @Override
    public void performAction() {
		List<SCV> scvs = getAgent().getSubordinateAgents(SCV.class);
        for (SCV scv : scvs) {
			if(!(scv.getGoal() instanceof HarvestMineralsGoal)){
				new HarvestMineralsCommand(scv, this.getAgent()).issueCommand();
			}
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ManageHarvest other = (ManageHarvest) obj;
        if (!Objects.equals(this.mineralShare, other.mineralShare)) {
            return false;
        }
        return true;
    }
    
}
