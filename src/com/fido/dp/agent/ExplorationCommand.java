/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import bwapi.Position;
import bwapi.Unit;
import bwta.BWTA;
import com.fido.dp.UnitInfo;
import bwta.BaseLocation;
import bwta.Region;
import com.fido.dp.BaseLocationInfo;
import com.fido.dp.base.GameAPI;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.Activity;
import com.fido.dp.activity.StrategicExploration;
import com.fido.dp.base.Goal;
import com.fido.dp.base.Order;
import com.fido.dp.goal.StrategicExplorationGoal;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.info.Info;
import com.fido.dp.info.EnemyBuildingDiscovered;
import com.fido.dp.info.LocationExploredInfo;
import com.fido.dp.order.ExploreBaseLocationOrder;
import com.fido.dp.request.Request;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class ExplorationCommand extends CommandAgent {
	
	private final ArrayList<BaseLocationInfo> baseLocations;
	
	private final ArrayList<UnitInfo> enemyUnits;
	
//	private final HashMap<

	
	
	
	public ArrayList<BaseLocationInfo> getBaseLocations() {
		return baseLocations;
	}
	
	
	

	public ExplorationCommand() {
		baseLocations = new ArrayList<>();
		for(BaseLocation baseLocation : bwta.BWTA.getStartLocations()) {
			boolean isOurBase = baseLocation.getTilePosition().equals(GameAPI.getGame().self().getStartLocation());
			baseLocations.add(new BaseLocationInfo(baseLocation, isOurBase));
		}
		
		enemyUnits = new ArrayList<>();
	}
	
	
	

    @Override
    protected Activity chooseAction() {
		if(getGoal() instanceof StrategicExplorationGoal){
			return new StrategicExploration(this);
		}
		return null;
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
//			Region baseRegion = BWTA.getRegion(((LocationExploredInfo) info).getBaseLocation());
//			Position basePosition = ((LocationExploredInfo) info).getBaseLocation();
//			
//			// find what base it is
//			for (BaseLocationInfo baseLocation : baseLocations) {
////				if(BWTA.getRegion(baseLocation.getPosition()).equals(baseRegion)){
//				if(baseLocation.getPosition().equals(basePosition)){
//					
//					// determine if it is enemy base
//					for (UnitInfo unitInfo : enemyUnits) {
//						if(BWTA.getRegion(unitInfo.getPosition()).equals(baseRegion)){
//							baseLocation.setIsEnemyBase(true);
//							new EnemyBaseDiscovered(geCommandAgent(), this, baseLocation).send();
//							break;
//						}
//					}
//					baseLocation.setExpplored(true);
//					baseLocation.setExplorationInProgress(false);
//					break;
//				}
//			}
			
		}
		else if(info instanceof EnemyBuildingDiscovered){
			EnemyBuildingDiscovered enemyBuildingDiscoveredRequest = (EnemyBuildingDiscovered) info;
			Unit unit = enemyBuildingDiscoveredRequest.getBuilding();
			addEnemyBuilding(new UnitInfo(unit, unit.getPosition()));
		}
	}

	@Override
	protected void handleCompletedOrder(Order order) {
		if(order instanceof ExploreBaseLocationOrder){
			ExploreBaseLocationOrder exploreBaseLocationOrder = (ExploreBaseLocationOrder) order;
			Region baseRegion = BWTA.getRegion(exploreBaseLocationOrder.getBaseLocation());
			Position basePosition = exploreBaseLocationOrder.getBaseLocation();
			
			// find what base it is
			for (BaseLocationInfo baseLocation : baseLocations) {
//				if(BWTA.getRegion(baseLocation.getPosition()).equals(baseRegion)){
				if(baseLocation.getPosition().equals(basePosition)){
					
					// determine if it is enemy base
					for (UnitInfo unitInfo : enemyUnits) {
						if(BWTA.getRegion(unitInfo.getPosition()).equals(baseRegion)){
							baseLocation.setIsEnemyBase(true);
							new EnemyBaseDiscovered(geCommandAgent(), this, baseLocation).send();
							break;
						}
					}
					baseLocation.setExpplored(true);
					baseLocation.setExplorationInProgress(false);
					break;
				}
			}
		}
	}
	
	
	
	private void addEnemyBuilding(UnitInfo building){
		enemyUnits.add(building);
		
	}

	@Override
	protected Goal getDefaultGoal() {
		return new StrategicExplorationGoal(this, null);
	}
	
	
    
}
