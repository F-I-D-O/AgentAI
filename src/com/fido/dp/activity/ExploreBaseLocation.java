/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import com.fido.dp.base.UnitActivity;
import bwapi.Position;
import com.fido.dp.Scout;
import com.fido.dp.agent.unit.UnitAgent;
import com.fido.dp.base.Activity;
import com.fido.dp.base.GameAPI;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.info.LocationExploredInfo;
import java.util.Objects;

/**
 *
 * @author david_000
 * @param <A>
 */
public class ExploreBaseLocation<A extends UnitAgent & Scout> extends UnitActivity<A,ExploreBaseLocationGoal> {
    
    private boolean locationExplored;
    
    private Position baseLocation;

    public ExploreBaseLocation(A unitAgent, Position location) {
        super(unitAgent);
        locationExplored = false;
        baseLocation = location;
    }

    @Override
    public void performAction() {
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
	public void initialize(ExploreBaseLocationGoal goal) {
		locationExplored = false;
		baseLocation = goal.getBaseLocation();
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
    
    
}
