/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.action.BBCBuild;
import com.fido.dp.BuildPlan;
import com.fido.dp.action.Action;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class BuildCommand extends CommandAgent {
    
    private ArrayList<SCV> freeWorkers;
    
    private ArrayList<BuildPlan> buildPlans;

    @Override
    protected Action chooseAction() {
        return getCommandedAction();
    }

    public void commandBBCBuild() {
        setCommandedAction(new BBCBuild(this));
    }

    public boolean needWorkes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
