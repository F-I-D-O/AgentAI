/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity;

import bwapi.Position;
import ninja.fido.agentai.base.CommandActivity;
import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.Scout;
import ninja.fido.agentai.agent.ExplorationCommand;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.GameAgent;
import ninja.fido.agentai.order.ExploreBaseLocationOrder;
import ninja.fido.agentai.order.MoveOrder;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A>
 */
public class StrategicExploration<A extends ExplorationCommand> extends CommandActivity<A,Goal> {
    
    private final ArrayList<Scout> scouts;

    public StrategicExploration(A agent) {
        super(agent);
        scouts = new ArrayList<>();
       
    }

    @Override
    public void performAction() {
//        Log.log(this, Level.FINE, "{0}:{1} Number of subordinate Agents: {2}", this.getClass(), getAgent().getClass(),
//                ((CommandAgent) getAgent()).getCommandedAgents().size());
		for (Agent subordinateAgent :  getAgent().getCommandedAgents()) {
			if(subordinateAgent instanceof Scout && !scouts.contains(subordinateAgent)){
				scouts.add((Scout) subordinateAgent);
			}
        }
        
        Log.log(this, Level.FINE, "{0}: Number of scouts: {1}", this, scouts.size());
		
        for (Scout scout : scouts) {
			if(!agent.isCommandedAgentOccupied((Agent) scout)){
				Position target = null;
				for (BaseLocationInfo baseInfo : agent.getBaseLocations()) {
					if(baseInfo.isStartLocation() && !baseInfo.isExpplored() && !baseInfo.isExplorationInProgress() 
							&& !baseInfo.isOurBase()){
						target = baseInfo.getPosition(); 
						baseInfo.setExplorationInProgress(true);
						new ExploreBaseLocationOrder(scout, this.getAgent(), target).issueOrder();
						break;
					}
				}
				
				// if there are no unexplored bases
				if(target == null && scout.getPosition().getDistance(GameAPI.getStartBasePosition()) 
						> Move.DEFAULT_MAX_DISTANCE_FROM_TARGET){
					new MoveOrder((GameAgent) scout, agent, GameAPI.getStartBasePosition()).issueOrder();
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
        final StrategicExploration other = (StrategicExploration) obj;
        return true;
    }

	@Override
	protected void init() {
		
	}
    
    
    
}
