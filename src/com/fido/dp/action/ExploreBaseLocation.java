/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import com.fido.dp.base.LeafAgent;
import com.fido.dp.GameAPI;
import java.util.Objects;

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
            runChildAction(new Move(this.getUnitAgent(), GameAPI.getGame().self().getStartLocation().toPosition()));
        }
        else{
            runChildAction(new Move(this.getUnitAgent(), baseLocation));
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExploreBaseLocation other = (ExploreBaseLocation) obj;
        if (!Objects.equals(this.baseLocation, other.baseLocation)) {
            return false;
        }
        return true;
    }
    
    
    
}
