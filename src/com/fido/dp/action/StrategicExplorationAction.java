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
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.Goal;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.order.ExploreBaseLocationOrder;
import com.fido.dp.order.MoveOrder;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A>
 */
public class StrategicExplorationAction<A extends ExplorationCommand> extends CommandAction<A,Goal> {
    
    private final ArrayList<Scout> scouts;

    public StrategicExplorationAction(A agent) {
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
			if(!agent.isSubordinateAgentOccupied((Agent) scout)){
				Position target = null;
				for (BaseLocationInfo baseInfo : agent.getBaseLocations()) {
					if(!baseInfo.isExpplored() && !baseInfo.isExplorationInProgress() && !baseInfo.isOurBase()){
						target = baseInfo.getPosition(); 
						baseInfo.setExplorationInProgress(true);
						new ExploreBaseLocationOrder(scout, this.getAgent(), target).issueOrder();
						break;
					}
				}
				
				// if there are no unexplored bases
				if(target == null && scout.getPosition().getDistance(GameAPI.getStartBasePosition()) 
						> Move.DEFAULT_MAX_DISTANCE_FROM_TARGET){
					new MoveOrder((UnitAgent) scout, agent, GameAPI.getStartBasePosition()).issueOrder();
				}
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
