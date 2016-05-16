/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent;

import bwapi.Position;
import bwapi.Unit;
import bwta.BWTA;
import ninja.fido.agentSCAI.UnitInfo;
import bwta.BaseLocation;
import bwta.Region;
import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.base.CommandAgent;
import ninja.fido.agentSCAI.base.Activity;
import ninja.fido.agentSCAI.activity.StrategicExploration;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.base.Order;
import ninja.fido.agentSCAI.goal.StrategicExplorationGoal;
import ninja.fido.agentSCAI.info.EnemyBaseDiscovered;
import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.info.EnemyBuildingDiscovered;
import ninja.fido.agentSCAI.info.LocationExploredInfo;
import ninja.fido.agentSCAI.order.ExploreBaseLocationOrder;
import ninja.fido.agentSCAI.base.Request;
import ninja.fido.agentSCAI.info.ExpansionInfo;
import ninja.fido.agentSCAI.request.ExpansionInfoRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import ninja.fido.agentSCAI.activity.Wait;
import ninja.fido.agentSCAI.goal.WaitGoal;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

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
	
	
	

	public ExplorationCommand() throws EmptyDecisionTableMapException {
		baseLocations = new ArrayList<>();
		for(BaseLocation baseLocation : bwta.BWTA.getBaseLocations()) {
			boolean isOurBase = baseLocation.getTilePosition().equals(GameAPI.getGame().self().getStartLocation());
			baseLocations.add(new BaseLocationInfo(baseLocation, isOurBase));
		}
		
		enemyUnits = new ArrayList<>();
	}
	
	@Override
	public Map<Class<? extends Goal>,Activity> getDefaultGoalActivityMap() {
		Map<Class<? extends Goal>,Activity> defaultActivityMap = new HashMap<>();

		defaultActivityMap.put(WaitGoal.class, new Wait());
		defaultActivityMap.put(StrategicExplorationGoal.class, new StrategicExploration());

		return defaultActivityMap;
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
