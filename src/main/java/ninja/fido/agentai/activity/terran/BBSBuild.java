/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.activity.terran;

import ninja.fido.agentai.base.CommandActivity;
import bwapi.UnitType;
import ninja.fido.agentai.BuildPlan;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.agent.BuildCommand;
import ninja.fido.agentai.base.Goal;
import java.util.logging.Level;
import java.util.logging.Logger;

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
