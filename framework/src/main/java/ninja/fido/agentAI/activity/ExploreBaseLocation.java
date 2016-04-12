/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.activity;

import ninja.fido.agentAI.base.UnitActivity;
import bwapi.Position;
import ninja.fido.agentAI.Scout;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.base.Activity;
import ninja.fido.agentAI.base.GameAPI;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentAI.info.LocationExploredInfo;
import java.util.Objects;
import ninja.fido.agentAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author david_000
 * @param <A>
 */
public class ExploreBaseLocation<A extends UnitAgent & Scout> extends UnitActivity<A,ExploreBaseLocationGoal>
		implements DecisionModuleActivity<A, ExploreBaseLocationGoal, ExploreBaseLocation>{
    
    private boolean locationExplored;
    
    private Position baseLocation;

	
	
	
	public ExploreBaseLocation() {
	}

    public ExploreBaseLocation(A unitAgent, Position location) {
        super(unitAgent);
        locationExplored = false;
        baseLocation = location;
    }

	public ExploreBaseLocation(A agent, ExploreBaseLocationGoal goal) {
		super(agent);
		locationExplored = false;
		baseLocation = goal.getBaseLocation();
	}

	
	
	
    @Override
    public void performAction() throws ChainOfCommandViolationException {
        if(locationExplored){
            runChildActivity(new Move(agent, GameAPI.getGame().self().getStartLocation().toPosition()));
        }
        else{
            runChildActivity(new Move(agent, baseLocation));
        }
    }

    @Override
    protected void onChildActivityFinish(Activity activity) {
		super.onChildActivityFinish(activity);
//        if(locationExplored){
			if(agent.getGoal() instanceof ExploreBaseLocationGoal){
				((ExploreBaseLocationGoal) agent.getGoal()).setLocationExplored(baseLocation);
			}
			new LocationExploredInfo(agent.getCommandAgent(), agent, baseLocation).send();
            finish();
//        }
//		else{
//			locationExplored = true;
//			
//		}
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
	

	@Override
	protected void init() {
		
	}
    
//	boolean isEnemyBuildingInRegion(Region region) {
//		for (const auto & kv : _unitData[_enemy].getUnits()) {
//			const UnitInfo & ui(kv.second);
//			if (ui.type.isBuilding()) 
//			{
//				if (BWTA::getRegion(BWAPI::TilePosition(ui.lastPosition)) == region) 
//				{
//					return true;
//				}
//			}
//		}
//
//		return false;
//	}

	@Override
	public ExploreBaseLocation create(A agent, ExploreBaseLocationGoal goal) {
		return new ExploreBaseLocation(agent, goal);
	}
    
    
}
