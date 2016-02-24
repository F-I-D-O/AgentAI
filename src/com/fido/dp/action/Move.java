/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.Position;
import com.fido.dp.agent.LeafAgent;

/**
 *
 * @author david_000
 */
public class Move extends UnitAction {
    
    private Position target;
    
    private boolean onMove;

    public Move(LeafAgent unitAgent, Position target) {
        super(unitAgent);
    }

    @Override
    public void run() {
        if(!onMove){
            if(!target.isValid()){
                fail("Invalid target");
            }
            getUnitAgent().getUnit().move(target);
            
            onMove = true;
        }
//        if()
    }
    
}
