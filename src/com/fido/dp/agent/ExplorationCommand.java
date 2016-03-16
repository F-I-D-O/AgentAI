/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Unit;
import bwta.BWTA;
import com.fido.dp.UnitInfo;
import bwta.BaseLocation;
import bwta.Region;
import com.fido.dp.BaseLocationInfo;
import com.fido.dp.GameAPI;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Action;
import com.fido.dp.action.StrategicExplorationAction;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.info.Info;
import com.fido.dp.info.EnemyBuildingDiscovered;
import com.fido.dp.info.LocationExploredInfo;
import com.fido.dp.request.Request;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class ExplorationCommand extends CommandAgent {
	
	private final ArrayList<BaseLocationInfo> bases;
	
	private final ArrayList<UnitInfo> enemyUnits;
	
//	private final HashMap<

	
	
	
	public ArrayList<BaseLocationInfo> getBases() {
		return bases;
	}
	
	
	

	public ExplorationCommand() {
		bases = new ArrayList<>();
		for(BaseLocation baseLocation : bwta.BWTA.getBaseLocations()) {
			boolean isOurBase = baseLocation.getTilePosition().equals(GameAPI.getGame().self().getStartLocation());
			bases.add(new BaseLocationInfo(baseLocation, isOurBase));
		}
		
		enemyUnits = new ArrayList<>();
	}
	
	
	

    @Override
    protected Action chooseAction() {
        return new StrategicExplorationAction(this);
    }
    
    public int getNumberOfScouts(){
        return getNumberOfSubordinateAgents();
    }

	@Override
	protected void handleRequest(Request request) {
		super.handleRequest(request); 
		
		
		
	}

	@Override
	protected void processInfo(Info info) {
		if(info instanceof LocationExploredInfo){
			Region baseRegion = BWTA.getRegion(((LocationExploredInfo) info).getBaseLocation());
			
			// find what base it is
			for (BaseLocationInfo baseLocation : bases) {
				if(BWTA.getRegion(baseLocation.getPosition()).equals(baseRegion)){
					
					// determine if it is enemy base
					for (UnitInfo unitInfo : enemyUnits) {
						if(BWTA.getRegion(unitInfo.getPosition()).equals(baseRegion)){
							baseLocation.setIsEnemyBase(true);
							new EnemyBaseDiscovered(geCommandAgent(), this, baseLocation).send();
							break;
						}
					}
					baseLocation.setExpplored(true);
				}
				break;
			}
			
		}
		else if(info instanceof EnemyBuildingDiscovered){
			EnemyBuildingDiscovered enemyBuildingDiscoveredRequest = (EnemyBuildingDiscovered) info;
			Unit unit = enemyBuildingDiscoveredRequest.getBuilding();
			addEnemyBuilding(new UnitInfo(unit, unit.getPosition()));
		}
	}
	
	
	
	private void addEnemyBuilding(UnitInfo building){
		enemyUnits.add(building);
		
	}
	
	
    
}
