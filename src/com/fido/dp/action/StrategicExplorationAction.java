/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwta.BaseLocation;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Scout;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.LeafAgent;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class StrategicExplorationAction extends CommandAction {
    
    private final ArrayList<Scout> scouts;

    public StrategicExplorationAction(CommandAgent agent) {
        super(agent);
        scouts = new ArrayList<>();
       
    }

    @Override
    public void performAction() {
        Log.log(this, Level.FINE, "{0}:{1} Number of subordinate Agents: {2}", this.getClass(), getAgent().getClass(),
                ((CommandAgent) getAgent()).getSubordinateAgents().size());
         for (Agent subordinateAgent :  getAgent().getSubordinateAgents()) {
             if(subordinateAgent instanceof Scout && !scouts.contains(subordinateAgent)){
                scouts.add((Scout) subordinateAgent);
             }
        }
        
        Log.log(this, Level.FINE, "{0}: Number of scouts: {1}", this, scouts.size());
        for (Scout scout : scouts) {
            Log.log(this, Level.FINE, "{0}: Nearest base: {1}", this.getClass(), 
                    bwta.BWTA.getBaseLocations());
            BaseLocation target = null;
            for(BaseLocation baseLocation : bwta.BWTA.getBaseLocations()) {
                if(!baseLocation.getTilePosition().equals(GameAPI.getGame().self().getStartLocation())){
                    target = baseLocation; 
                    Log.log(this, Level.FINER, "Our base position: {0}", GameAPI.getGame().self().getStartLocation().toPosition());
                    Log.log(this, Level.FINER, "Target base position: {0}", baseLocation.getPosition());
                    break;
                }
            }
            if(target != null){
                scout.commandExploreBaseLocation(target.getPosition());
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
    
    
    
}
