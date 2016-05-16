/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.agent.unit;

import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.agent.Scout;
import ninja.fido.agentSCAI.activity.ConstructBuilding;
import ninja.fido.agentSCAI.activity.ExploreBaseLocation;
import ninja.fido.agentSCAI.activity.HarvestMinerals;
import ninja.fido.agentSCAI.activity.Move;
import ninja.fido.agentSCAI.activity.StartExpansion;
import ninja.fido.agentSCAI.base.GameAPI;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentSCAI.modules.decisionMaking.GoalParameter;
import ninja.fido.agentSCAI.goal.ConstructBuildingGoal;
import ninja.fido.agentSCAI.goal.ExploreBaseLocationGoal;
import ninja.fido.agentSCAI.goal.HarvestMineralsGoal;
import ninja.fido.agentSCAI.goal.MoveGoal;
import ninja.fido.agentSCAI.goal.StartExpansionGoal;
import ninja.fido.agentSCAI.info.UnitCreationStartedInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 *
 * @author F.I.D.O.
 * @param <A> Worker agent type.
 */
public abstract class Worker<A extends Worker> extends UnitAgent<A> implements Scout {
	
	private boolean constructionInProgress;
	
	protected boolean constructionProcessInProgress;
	
	protected UnitType constructedBuildingType;
	
	protected boolean invalidBuildPositionFailure;
		
	
	public boolean isConstructingBuilding() {
		return constructionProcessInProgress;
	}

	public boolean isInvalidBuildPositionFailure() {
		return invalidBuildPositionFailure;
	}

	public Worker() throws EmptyDecisionTableMapException {
	}
	
	
	public Worker(Unit unit) throws EmptyDecisionTableMapException {
		super(unit);
	}
	
	@Override
	public Position getPosition() {
		return unit.getPosition();
	}
	
	public void onConstructionStarted() throws ResourceDeficiencyException{
		Log.log(this, Level.INFO, "{0}: onConstructionStarted", this.getClass());
		spendResource(ResourceType.GAS, constructedBuildingType.gasPrice());
		spendResource(ResourceType.MINERALS, constructedBuildingType.mineralPrice());
//		spendSupply(ResourceType.SUPPLY, constructedBuildingType.supplyRequired());
		constructionInProgress = true;
		new UnitCreationStartedInfo(getCommandAgent(), this, constructedBuildingType).send();
	}
	
	public void onConstructionFinished() {
		Log.log(this, Level.INFO, "{0}: onConstructionFinished", this.getClass());
		constructionInProgress = false;
		constructionProcessInProgress = false;
		constructedBuildingType = null;
		if(getGoal() instanceof ConstructBuildingGoal){
			((ConstructBuildingGoal) getGoal()).setBuildingConstructionFinished(true);
		}
	}
	
	public boolean haveEnoughResourcersToBuild(UnitType buildingType){
		return buildingType.mineralPrice() <= getOwnedMinerals() && buildingType.gasPrice() <= getOwnedGas();
	}
	
	public int getMissingMinerals(UnitType buildingType){
		int difference = buildingType.mineralPrice() - getOwnedMinerals();
		return difference > 0 ? difference : 0;
	}
	
	public int getMissingGas(UnitType buildingType){
		int difference = buildingType.gasPrice() - getOwnedGas();
		return difference > 0 ? difference : 0;
	}
	
	public void build(UnitType buildingType, TilePosition placeToBuildOn){
		invalidBuildPositionFailure = false;
		GameAPI.build(this, buildingType, placeToBuildOn);
		constructionProcessInProgress = true;
		constructedBuildingType = buildingType;
	}

	public void handleInvalidBuildPosition(TilePosition targetTilePosition) {
		invalidBuildPositionFailure = true;
	}

	@Override
	public Map<DecisionTablesMapKey, DecisionTable> getDefaultDecisionTablesMap() {
		Map<DecisionTablesMapKey, DecisionTable> defaultDecisionTablesMap = new HashMap<>();
		
		TreeMap<Double,DecisionModuleActivity> actionMap = new TreeMap<>();
		actionMap.put(1.0, new HarvestMinerals());
		DecisionTablesMapKey key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(HarvestMineralsGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ExploreBaseLocation());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ExploreBaseLocationGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new Move());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(MoveGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new StartExpansion());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(StartExpansionGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		actionMap = new TreeMap<>();
		actionMap.put(1.0, new ConstructBuilding());
		key =  new DecisionTablesMapKey();
		key.addParameter(new GoalParameter(ConstructBuildingGoal.class));
		defaultDecisionTablesMap.put(key, new DecisionTable(actionMap));
		
		return defaultDecisionTablesMap;
	}
	
	
	
}
