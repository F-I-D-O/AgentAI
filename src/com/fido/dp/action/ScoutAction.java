/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.LeafAgent;

/**
 *
 * @author david_000
 */
public class ScoutAction extends UnitAction{

    public ScoutAction(LeafAgent unitAgent) {
        super(unitAgent);
    }

    @Override
    public void performAction() {
        if(getUnitAgent().isIdle()){
            
        }
    }
    
}
