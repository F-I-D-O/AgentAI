/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.Log;
import com.fido.dp.base.LeafAgent;
import java.util.Objects;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class Move extends UnitAction {
    
    private Position target;
    
    private double maxDistanceFromTarget;
    
    private boolean onMove;

    
    
    
    public Position getTarget() {
        return target;
    }
    
    
    
    

    public Move(LeafAgent unitAgent, Position target) {
        super(unitAgent);
        this.target = target;
        maxDistanceFromTarget = 32;
        onMove = false;
    }

    @Override
    public void performAction() {
        Unit unit = getUnitAgent().getUnit();
        if(!onMove){
            if(!target.isValid()){
                fail("Invalid target");
            }
            Log.log(this, Level.FINE, "{0}: {1} On position: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), this.getUnitAgent().getUnit().getPosition());
            Log.log(this, Level.FINE, "{0}: {1} target position: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), target);
            unit.move(target);
            
            onMove = true;
        }
        if(unit.getPosition().getDistance(target) <= maxDistanceFromTarget){
            Log.log(this, Level.FINE, "{0}: {1} Reached target: {2}", this.getAgent().getClass(), 
                    this.getAgent().getClass(), target);
            unit.stop();
            finish();
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
        final Move other = (Move) obj;
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        if (Double.doubleToLongBits(this.maxDistanceFromTarget) != Double.doubleToLongBits(other.maxDistanceFromTarget)) {
            return false;
        }
        return true;
    }
    
    
    
}
