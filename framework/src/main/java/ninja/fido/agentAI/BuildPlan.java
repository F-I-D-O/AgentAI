/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import bwapi.UnitType;

/**
 * Build plan.
 * @author david_000
 */
public class BuildPlan implements Comparable<BuildPlan>{

	/**
	 * Build plan priority.
	 */
    private int priority;
    
	/**
	 * Building type.
	 */
    private final UnitType buildingType;

    
    
    
	/**
	 * Returns building type.
	 * @return Returns building type.
	 */
    public UnitType getBuildingType() {
        return buildingType;
    }

    
    
    
    /**
	 * Constructor.
	 * @param priority Build plan priority.
	 * @param buildingType Returns building type.
	 */
    public BuildPlan(int priority, UnitType buildingType) {
        this.priority = priority;
        this.buildingType = buildingType;
    }
    
    
    
    
    @Override
    public int compareTo(BuildPlan buildPlan) {
        return priority - buildPlan.priority;
    }
    
	/**
	 * Returns building gas price.
	 * @return Returns building gas price.
	 */
    public int getGasPrice(){
        return buildingType.gasPrice();
    }
	
    /**
	 * Returns building mineral price.
	 * @return Returns building mineral price.
	 */
    public int getMineralsPrice(){
        return buildingType.mineralPrice();
    }
    
}
