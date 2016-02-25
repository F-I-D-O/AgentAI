/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.agent.LeafAgent;

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
    }

    @Override
    public void performAction() {
        Unit unit = getUnitAgent().getUnit();
        if(!onMove){
            if(!target.isValid()){
                fail("Invalid target");
            }
            unit.move(target);
            
            onMove = true;
        }
        if(unit.getPosition().getDistance(target) <= maxDistanceFromTarget){
            unit.stop();
            finish();
        }
    }
    
}
