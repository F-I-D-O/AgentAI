/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.action.BBCBuild;
import com.fido.dp.BuildPlan;
import com.fido.dp.action.Action;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class BuildingConstructionCommand extends CommandAgent{
    
    public enum  BuildingConstructionCommandState{
        OK,
        MISSING_WORKERS,
        MISSING_GAS,
        MISSING_MINERALS
    }
    
    
    
    private final ArrayList<SCV> freeWorkers;
    
    private final ArrayList<BuildPlan> buildPlans;

    
    
    
    public BuildingConstructionCommand() {
        this.freeWorkers = new ArrayList<>();
        this.buildPlans = new ArrayList<>();
    }
    
    
    

    @Override
    protected Action chooseAction() {
        return getCommandedAction();
    }

    public void commandBBCBuild() {
        setCommandedAction(new BBCBuild(this));
    }

    public boolean needWorkes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public BuildingConstructionCommandState automaticBuild(){
        if(buildPlans.isEmpty()){
            return BuildingConstructionCommandState.OK;
        }
        
        buildPlans.sort(null);
        for (BuildPlan buildPlan : buildPlans) {
            if(freeWorkers.isEmpty()){
                return BuildingConstructionCommandState.MISSING_WORKERS;
            }
            if(buildPlan.getGasPrice() > getOwnedGas()){
                return BuildingConstructionCommandState.MISSING_GAS;
            }
            if(buildPlan.getMineralsPrice() > getOwnedMinerals()){
                return BuildingConstructionCommandState.MISSING_MINERALS;
            }
            
            constructBuilding(buildPlan);
        }
        
        return BuildingConstructionCommandState.OK;
    }

    public void constructBuilding(BuildPlan buildPlan){
        
    }
    
    public BuildPlan getNextBuildPlan(){
        buildPlans.sort(null);
        return buildPlans.get(0);
    }
    
    public void addBuildPlan(BuildPlan buildPlan){
        buildPlans.add(buildPlan);
    }
}
