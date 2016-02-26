/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwta.BaseLocation;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.LeafAgent;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class StrategicExplorationAction extends Action {
    
    private final ArrayList<LeafAgent> scouts;

    public StrategicExplorationAction(CommandAgent agent) {
        super(agent);
        scouts = new ArrayList<>();
       
    }

    @Override
    public void performAction() {
        Log.log(this, Level.FINE, "{0}:{1} Number of subordinate Agents: {2}", this.getClass(), getAgent().getClass(),
                ((CommandAgent) getAgent()).getSubordinateAgents().size());
         for (Agent subordinateAgent : ((CommandAgent) getAgent()).getSubordinateAgents()) {
//            if(subordinateAgent instanceof Vulture){
             if(!scouts.contains(subordinateAgent)){
                scouts.add((LeafAgent) subordinateAgent);
             }
//            }
        }
        
        Log.log(this, Level.FINE, "{0}: Number of scouts: {1}", this, scouts.size());
        for (LeafAgent scout : scouts) {
            Log.log(this, Level.FINE, "{0}: Nearest base: {1}", this.getClass(), 
                    bwta.BWTA.getBaseLocations());
            BaseLocation target = null;
            for(BaseLocation baseLocation : bwta.BWTA.getBaseLocations()) {
                if(!baseLocation.getTilePosition().equals(GameAPI.getGame().self().getStartLocation())){
                    target = baseLocation; 
                    Log.log(this, Level.FINE, "Our base position: {0}", GameAPI.getGame().self().getStartLocation().toPosition());
                    Log.log(this, Level.FINE, "Target base position: {0}", baseLocation.getPosition());
                    break;
                }
            }
            if(target != null){
                scout.setCommandedAction(new ExploreBaseLocation(
                        scout, target.getPosition()));
            }
        }
    }
    
}
