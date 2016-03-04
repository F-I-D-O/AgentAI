/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import bwapi.UnitType;
import com.fido.dp.BuildPlan;
import com.fido.dp.agent.BuildingConstructionCommand;

/**
 *
 * @author david_000
 */
public class BBCBuild extends CommandAction {
    
    

    public BBCBuild(BuildingConstructionCommand agent) {
        super(agent);
    }

    @Override
    protected void init() {
        super.init(); 
        getAgent().addBuildPlan(new BuildPlan(10, UnitType.Terran_Barracks));
        getAgent().addBuildPlan(new BuildPlan(10, UnitType.Terran_Barracks));
        getAgent().addBuildPlan(new BuildPlan(10, UnitType.Terran_Supply_Depot));
    }

    @Override
    public BuildingConstructionCommand getAgent() {
        return (BuildingConstructionCommand) agent; //To change body of generated methods, choose Tools | Templates.
    }



    @Override
    public void performAction() {
        switch(getAgent().automaticBuild()){
            case MISSING_GAS:
                break;
            case MISSING_MINERALS:
                break;
            case MISSING_WORKERS:
                break;
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
        final BBSStrategy other = (BBSStrategy) obj;
        return true;
    }
    
}
