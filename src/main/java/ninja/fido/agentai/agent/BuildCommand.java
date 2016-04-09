/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.agent;

import ninja.fido.agentai.base.CommandAgent;
import bwapi.TilePosition;
import bwapi.UnitType;
import ninja.fido.agentai.activity.terran.BBSBuild;
import ninja.fido.agentai.BuildPlan;
import ninja.fido.agentai.Building;
import ninja.fido.agentai.BuildingPlacer;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.Tools;
import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.order.ConstructBuildingOrder;
import ninja.fido.agentai.goal.BBSBuildGoal;
import ninja.fido.agentai.base.Info;
import ninja.fido.agentai.base.Request;
import ninja.fido.agentai.info.UnitCreationStartedInfo;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class BuildCommand extends CommandAgent{

    public enum  BuildCommandState{
        OK,
        MISSING_WORKERS,
        MISSING_GAS,
        MISSING_MINERALS
    }
    
    
    
	
    private final Queue<Worker> freeWorkers;
    
    private final ArrayList<BuildPlan> buildPlans;
	
	private final BuildingPlacer buildingPlacer;

    private int numberOfMissingWorkers;
	
	private final HashMap<UnitType,Integer> numberOfConstrustionStarted;
    

	
	
    public BuildCommand() {
        freeWorkers = new ArrayDeque<>();
        this.buildPlans = new ArrayList<>();
		numberOfMissingWorkers = 0;
//		buildingPlacer = new UAlbertaBuildingPlacer();
		buildingPlacer = GameAPI.getBuildingPlacer();
		numberOfConstrustionStarted = new HashMap<>();
    }
    
    
	
    private void addWorker(Worker worker){
		freeWorkers.add(worker);
		if(numberOfMissingWorkers > 0){
			numberOfMissingWorkers--;
		}
	}

    public boolean needWorkes() {
        return numberOfMissingWorkers > 0;
    }
    
    public BuildCommandState automaticBuild() throws ResourceDeficiencyException{
        if(buildPlans.isEmpty()){
            return BuildCommandState.OK;
        }
        
        buildPlans.sort(null);
		Iterator<BuildPlan> iterator = buildPlans.iterator();
		while (iterator.hasNext()) {
			BuildPlan buildPlan = iterator.next();
			if(buildPlan.getGasPrice() > getOwnedGas()){
                return BuildCommandState.MISSING_GAS;
            }
            if(buildPlan.getMineralsPrice() > getOwnedMinerals()){
                return BuildCommandState.MISSING_MINERALS;
            }
            if(freeWorkers.isEmpty()){
				numberOfMissingWorkers++;
                return BuildCommandState.MISSING_WORKERS;
            }

            commandBuildingConstruction(buildPlan);
			iterator.remove();
        }
        
        return BuildCommandState.OK;
    }

    public void commandBuildingConstruction(BuildPlan buildPlan) throws ResourceDeficiencyException{        
		Log.log(this, Level.INFO, "{0}: Building construction commanded: {1}", this.getClass(), 
				buildPlan.getBuildingType().getClass());
		Worker worker = freeWorkers.poll();
		commandBuildingConstruction(buildPlan.getBuildingType(), worker);
    }
	
	public void commandBuildingConstruction(UnitType buildingType, Worker worker) throws ResourceDeficiencyException{        
		Log.log(this, Level.INFO, "{0}: Building construction commanded: {1}", this.getClass(), 
				buildingType.getClass());
		TilePosition buildingPlace = findPositionForBuild(buildingType, worker);
		giveResource(worker, ResourceType.MINERALS, buildingType.mineralPrice());
		new ConstructBuildingOrder(worker, this, buildingType, buildingPlace).issueOrder();
    }
    
    public BuildPlan getNextBuildPlan(){
        buildPlans.sort(null);
        return buildPlans.get(0);
    }
    
    public void addBuildPlan(BuildPlan buildPlan){
        buildPlans.add(buildPlan);
    }
	
	public int getMissingMineralForFirsItem() {
		if(buildPlans.isEmpty()){
			return 0;
		}
		
		BuildPlan firstPlan = buildPlans.get(0);
		return firstPlan.getMineralsPrice() - getOwnedMinerals();
	}
	
	public int getMissingCrystal() {
		if(buildPlans.isEmpty()){
			return 0;
		}
		int cost = 0;
		for (BuildPlan buildPlan : buildPlans) {
			cost += buildPlan.getMineralsPrice();
		}
		return cost - getOwnedMinerals();
	}
	
	public int getNumberOfConstructionStarted(UnitType unitType){
		return numberOfConstrustionStarted.containsKey(unitType) ? numberOfConstrustionStarted.get(unitType) : 0;
	}
	
	public Worker getWorker(){
		Worker worker = freeWorkers.poll();
		worker.setAssigned(false);
		return worker;
	}
	
	public int getNumberOfFreeWorkers(){
		return freeWorkers.size();
	}

	@Override
	protected void processInfo(Info info) {
		
	}

	
	
	
	@Override
	protected void handleRequest(Request request) {
		super.handleRequest(request); 
		if(request instanceof UnitCreationStartedInfo){
			UnitType type = ((UnitCreationStartedInfo) request).getUnitType();
			if(type.isBuilding()){
				incNumberOfConstructionStarted(type);
			}
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return  new BBSBuildGoal(this, null);
	}	

	@Override
	protected void routine() {
		super.routine(); 
		for (Worker worker : getCommandedAgents(Worker.class)) {
			if(!worker.IsAssigned()){
				addWorker(worker);
				worker.setAssigned(true);
			}
		}
	}
	
	
	
	
	private void incNumberOfConstructionStarted(UnitType unitType){
		Tools.incrementMapValue(numberOfConstrustionStarted, unitType);
	}
	

    @Override
    protected Activity chooseAction() {
        if(getGoal() instanceof BBSBuildGoal){
			return new BBSBuild(this);
		}
		return null;
    }

	
	private TilePosition findPositionForBuild(BuildPlan buildPlan, Worker worker) {
		return findPositionForBuild(buildPlan.getBuildingType(), worker);
	}
	
	private TilePosition findPositionForBuild(UnitType buildingType, Worker worker) {
		return buildingPlacer.getBuildingLocation(new Building(GameAPI.getGame().self().getStartLocation().toPosition(),
				buildingType, worker.getUnit(), false));
	}
	
	
	
}