/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import com.fido.dp.base.UnitActivity;
import bwapi.Position;
import com.fido.dp.base.GameAPI;
import com.fido.dp.agent.SCV;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import com.fido.dp.info.LocationExploredInfo;
import java.util.Objects;

/**
 *
 * @author david_000
 */
public class ExploreBaseLocation extends UnitActivity<SCV,Goal> {
    
    private boolean locationExplored;
    
    private final Position baseLocation;

    public ExploreBaseLocation(SCV unitAgent, Position location) {
        super(unitAgent);
        locationExplored = false;
        baseLocation = location;
    }

    @Override
    public void performAction() {
        if(locationExplored){
            runChildAction(new Move(agent, GameAPI.getGame().self().getStartLocation().toPosition()));
        }
        else{
            runChildAction(new Move(agent, baseLocation));
        }
    }

    @Override
    protected void onChildActionFinish() {
		super.onChildActionFinish();
//        if(locationExplored){
			if(agent.getGoal() instanceof ExploreBaseLocationGoal){
				((ExploreBaseLocationGoal) agent.getGoal()).setLocationExplored(baseLocation);
			}
			new LocationExploredInfo(agent.geCommandAgent(), agent, baseLocation).send();
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
    
    
}
