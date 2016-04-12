/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import bwapi.UnitType;

/**
 *
 * @author david_000
 */
public class BuildPlan implements Comparable<BuildPlan>{

    private int priority;
    
    private UnitType buildingType;

    
    
    
    public UnitType getBuildingType() {
        return buildingType;
    }

    
    
    
    
    public BuildPlan(int priority, UnitType buildingType) {
        this.priority = priority;
        this.buildingType = buildingType;
    }
    
    
    
    
    @Override
    public int compareTo(BuildPlan buildPlan) {
        return priority - buildPlan.priority;
    }
    
    public int getGasPrice(){
        return buildingType.gasPrice();
    }
    
    public int getMineralsPrice(){
        return buildingType.mineralPrice();
    }
    
}
