/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import com.fido.dp.agent.LeafAgent;
import com.fido.dp.GameAPI;

/**
 *
 * @author david_000
 */
public class ExploreBaseLocation extends UnitAction{
    
    private boolean locationExplored;
    
    private final Position baseLocation;

    public ExploreBaseLocation(LeafAgent unitAgent, Position location) {
        super(unitAgent);
        locationExplored = false;
        baseLocation = location;
    }

    @Override
    public void performAction() {
        if(locationExplored){
            runChildAction(new Move(this.getUnitAgent(), baseLocation));
        }
        else{
            runChildAction(new Move(this.getUnitAgent(), GameAPI.getGame().self().getStartLocation().toPosition()));
        }
    }

    @Override
    protected void onChildActionFinish() {
//        if(((Move)childAction).getTarget())
        if(locationExplored){
            finish();
        }
        
        locationExplored = true;
        super.onChildActionFinish();
    }
    
    
}
