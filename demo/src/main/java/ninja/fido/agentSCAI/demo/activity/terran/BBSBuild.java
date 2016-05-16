/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.terran;

import ninja.fido.agentSCAI.base.CommandActivity;
import bwapi.UnitType;
import ninja.fido.agentSCAI.BuildPlan;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.agent.BuildCommand;
import ninja.fido.agentSCAI.base.Goal;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A>
 */
public class BBSBuild<A extends BuildCommand> extends CommandActivity<A,Goal,BBSBuild> {

	public BBSBuild() {
	}

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

	@Override
	public BBSBuild create(A agent, Goal goal) {
		return new BBSBuild(agent);
	}
    
}
