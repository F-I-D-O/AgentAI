/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.terran;

import com.fido.dp.base.CommandActivity;
import bwapi.UnitType;
import com.fido.dp.BuildPlan;
import com.fido.dp.Log;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.base.Goal;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A>
 */
public class BBSBuild<A extends BuildCommand> extends CommandActivity<A,Goal> {
    
    

    public BBSBuild(A agent) {
        super(agent);
    }

    @Override
    protected void init() {
        agent.addBuildPlan(new BuildPlan(10, UnitType.Terran_Barracks));
        agent.addBuildPlan(new BuildPlan(10, UnitType.Terran_Barracks));
        agent.addBuildPlan(new BuildPlan(10, UnitType.Terran_Supply_Depot));
    }



    @Override
    public void performAction() {
		for (SCV scv : getAgent().<SCV>getSubordinateAgents(SCV.class)) {
			if(!scv.IsAssigned()){
				getAgent().addWorker(scv);
				scv.setAssigned(true);
			}
		}
		
        switch(getAgent().automaticBuild()){
            case MISSING_GAS:
				Log.log(this, Level.FINE, "{0}: Missing gas!", this.getClass());
                break;
            case MISSING_MINERALS:
				Log.log(this, Level.FINE, "{0}: Missing minerals!", this.getClass());
                break;
            case MISSING_WORKERS:
				Log.log(this, Level.FINE, "{0}: Missing workers!", this.getClass());
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
        final BBSBuild other = (BBSBuild) obj;
        return true;
    }
    
}
