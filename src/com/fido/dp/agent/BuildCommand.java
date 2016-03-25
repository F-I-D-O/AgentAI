/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.base.CommandAgent;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.activity.BBSBuild;
import com.fido.dp.BuildPlan;
import com.fido.dp.Building;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.base.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.Tools;
import com.fido.dp.UAlbertaBuildingPlacer;
import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import com.fido.dp.order.ConstructBuildingOrder;
import com.fido.dp.goal.BBSBuildGoal;
import com.fido.dp.request.Request;
import com.fido.dp.request.UnitCreationStartedInfo;
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
    
    
    
	
    private final Queue<SCV> freeWorkers;
    
    private final ArrayList<BuildPlan> buildPlans;
	
	private final BuildingPlacer buildingPlacer;

    private int numberOfMissingWorkers;
	
	private final HashMap<UnitType,Integer> numberOfConstrustionStarted;
    
    
	
	
    public BuildCommand() {
        this.freeWorkers = new ArrayDeque<>();
        this.buildPlans = new ArrayList<>();
		numberOfMissingWorkers = 0;
		buildingPlacer = new UAlbertaBuildingPlacer();
		numberOfConstrustionStarted = new HashMap<>();
    }
    
    
	
    public void addWorker(SCV worker){
		freeWorkers.add(worker);
		if(numberOfMissingWorkers > 0){
			numberOfMissingWorkers--;
		}
	}

    public boolean needWorkes() {
        return numberOfMissingWorkers > 0;
    }
    
    public BuildCommandState automaticBuild(){
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

            commandrBuildingConstruction(buildPlan);
			iterator.remove();
        }
        
        return BuildCommandState.OK;
    }

    public void commandrBuildingConstruction(BuildPlan buildPlan){        
		Log.log(this, Level.INFO, "{0}: Building construction commanded: {1}", this.getClass(), 
				buildPlan.getBuildingType().getClass());
		SCV worker = freeWorkers.poll();
		TilePosition buildingPlace = findPositionForBuild(buildPlan, worker);
		giveSupply(worker, Material.MINERALS, buildPlan.getMineralsPrice());
		new ConstructBuildingOrder(worker, this, buildPlan.getBuildingType(), buildingPlace).issueOrder();
    }
    
    public BuildPlan getNextBuildPlan(){
        buildPlans.sort(null);
        return buildPlans.get(0);
    }
    
    public void addBuildPlan(BuildPlan buildPlan){
        buildPlans.add(buildPlan);
    }
	
	public int getMissingCrystalForFirsItem() {
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

	
	private TilePosition findPositionForBuild(BuildPlan buildPlan, SCV scv) {
		return buildingPlacer.getBuildingLocation(new Building(GameAPI.getGame().self().getStartLocation().toPosition(),
				buildPlan.getBuildingType(), scv.getUnit(), false));
	}
	
	
	
}
