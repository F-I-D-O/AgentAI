/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.base.CommandAgent;
import bwapi.TilePosition;
import bwapi.UnitType;
import com.fido.dp.action.BBSBuild;
import com.fido.dp.BuildPlan;
import com.fido.dp.Building;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.GameAPI;
import com.fido.dp.UAlbertaBuildingPlacer;
import com.fido.dp.action.Action;
import com.fido.dp.action.ExploreBaseLocation;
import com.fido.dp.command.ConstructBuildingCommand;
import com.fido.dp.goal.BBSBuildGoal;
import com.fido.dp.goal.ExploreBaseLocationGoal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

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
    
    
	
    public BuildCommand() {
        this.freeWorkers = new ArrayDeque<>();
        this.buildPlans = new ArrayList<>();
		numberOfMissingWorkers = 0;
		buildingPlacer = new UAlbertaBuildingPlacer();
    }
    
    
    

    @Override
    protected Action chooseAction() {
        if(getGoal() instanceof BBSBuildGoal){
			return new BBSBuild(this);
		}
		return null;
    }

//    public void commandBBCBuild() {
//        setCommandedAction(new BBSBuild(this));
//    }
	
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
        for (BuildPlan buildPlan : buildPlans) {
            if(freeWorkers.isEmpty()){
				numberOfMissingWorkers++;
                return BuildCommandState.MISSING_WORKERS;
            }
            if(buildPlan.getGasPrice() > getOwnedGas()){
                return BuildCommandState.MISSING_GAS;
            }
            if(buildPlan.getMineralsPrice() > getOwnedMinerals()){
                return BuildCommandState.MISSING_MINERALS;
            }
            
            commandrBuildingConstruction(buildPlan);
        }
        
        return BuildCommandState.OK;
    }

    public void commandrBuildingConstruction(BuildPlan buildPlan){        
		SCV worker = freeWorkers.poll();
		TilePosition buildingPlace = findPositionForBuild(buildPlan, worker);
		new ConstructBuildingCommand(worker, this, buildPlan.getBuildingType(), buildingPlace).issueCommand();
    }
    
    public BuildPlan getNextBuildPlan(){
        buildPlans.sort(null);
        return buildPlans.get(0);
    }
    
    public void addBuildPlan(BuildPlan buildPlan){
        buildPlans.add(buildPlan);
    }
	
	private TilePosition findPositionForBuild(BuildPlan buildPlan, SCV scv) {
		return buildingPlacer.getBuildingLocation(new Building(GameAPI.getGame().self().getStartLocation().toPosition(),
				buildPlan.getBuildingType(), scv.getUnit(), false));
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
	
}
