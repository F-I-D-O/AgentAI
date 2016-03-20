/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import com.fido.dp.base.CommandAction;
import com.fido.dp.BaseLocationInfo;
import com.fido.dp.Log;
import com.fido.dp.Scout;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.base.Agent;
import com.fido.dp.order.ExploreBaseLocationOrder;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <T>
 */
public class StrategicExplorationAction<T extends ExplorationCommand> extends CommandAction<T> {
    
    private final ArrayList<Scout> scouts;

    public StrategicExplorationAction(T agent) {
        super(agent);
        scouts = new ArrayList<>();
       
    }

    @Override
    public void performAction() {
//        Log.log(this, Level.FINE, "{0}:{1} Number of subordinate Agents: {2}", this.getClass(), getAgent().getClass(),
//                ((CommandAgent) getAgent()).getSubordinateAgents().size());
		for (Agent subordinateAgent :  getAgent().getSubordinateAgents()) {
			if(subordinateAgent instanceof Scout && !scouts.contains(subordinateAgent)){
				scouts.add((Scout) subordinateAgent);
			}
        }
        
        Log.log(this, Level.FINE, "{0}: Number of scouts: {1}", this, scouts.size());
		
        for (Scout scout : scouts) {
			if(scout.getUnit().isIdle()){
				Position target = null;
				for (BaseLocationInfo baseInfo : agent.getBaseLocations()) {
					if(!baseInfo.isExpplored() && !baseInfo.isExplorationInProgress() && !baseInfo.isOurBase()){
						target = baseInfo.getPosition(); 
						baseInfo.setExplorationInProgress(true);
						new ExploreBaseLocationOrder(scout, this.getAgent(), target).issueCommand();
						break;
					}
					
					// if there are no unexplored bases
					return;
				}
				
//				for(BaseLocationInfo base : agent.getBases()) {
//					if(!base.isExpplored() && !base.isOurBase()){
//						target = base.getPosition(); 
//						Log.log(this, Level.FINER, "Our base position: {0}", target);
//						Log.log(this, Level.FINER, "Target base position: {0}", target);
//						break;
//					}
//				}
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
        final StrategicExplorationAction other = (StrategicExplorationAction) obj;
        return true;
    }

	@Override
	protected void init() {
		
	}
    
    
    
}
