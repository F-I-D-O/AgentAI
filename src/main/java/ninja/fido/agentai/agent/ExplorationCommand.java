/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import bwapi.Position;
import bwapi.Unit;
import bwta.BWTA;
import ninja.fido.agentai.UnitInfo;
import bwta.BaseLocation;
import bwta.Region;
import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.activity.StrategicExploration;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.base.Order;
import ninja.fido.agentai.goal.StrategicExplorationGoal;
import ninja.fido.agentai.info.EnemyBaseDiscovered;
import ninja.fido.agentai.base.Info;
import ninja.fido.agentai.info.EnemyBuildingDiscovered;
import ninja.fido.agentai.info.LocationExploredInfo;
import ninja.fido.agentai.order.ExploreBaseLocationOrder;
import ninja.fido.agentai.base.Request;
import ninja.fido.agentai.info.ExpansionInfo;
import ninja.fido.agentai.request.ExpansionInfoRequest;
import java.util.ArrayList;
import java.util.logging.Level;

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
		for(BaseLocation baseLocation : bwta.BWTA.getBaseLocations()) {
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
        return getNumberOfCommandedAgents();
    }

	@Override
	protected void handleRequest(Request request) {
		super.handleRequest(request); 
		if(request instanceof ExpansionInfoRequest){
			new ExpansionInfo(request.getSender(), this, getBestPositionToExpand()).send();
		}
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
//							new EnemyBaseDiscovered(getCommandAgent(), this, baseLocation).send();
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
							new EnemyBaseDiscovered(getCommandAgent(), this, baseLocation).send();
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

	private Position getBestPositionToExpand() {
		for (BaseLocationInfo baseLocationInfo : baseLocations) {
			if(!baseLocationInfo.isStartLocation() && !baseLocationInfo.isChosenForExpansion()){
				baseLocationInfo.setChosenForExpansion(true);
				return baseLocationInfo.getPosition();
			}
		}
		Log.log(this, Level.WARNING, "{0}: no base to expand!", this.getClass());
		return null;
	}
	
	
    
}
